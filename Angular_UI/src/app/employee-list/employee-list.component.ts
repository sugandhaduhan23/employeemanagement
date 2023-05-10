import {Component, OnInit, ViewChild} from '@angular/core';
import {MatPaginator} from '@angular/material/paginator';
import {MatSort} from '@angular/material/sort';
import {MatTableDataSource} from '@angular/material/table';
import { EmployeeService } from '../services/employee.service';
import { environment } from 'src/environments/environment';
import { MatDialog } from '@angular/material/dialog';
import { EmployeeComponent } from '../employee/employee.component';
import { DeleteDialogComponent } from '../delete-dialog/delete-dialog.component';
import { CommonService } from '../services/common.service';


@Component({
  selector: 'app-employee-list',
  templateUrl: './employee-list.component.html',
  styleUrls: ['./employee-list.component.css']
})
export class EmployeeListComponent implements OnInit{
  @ViewChild(MatPaginator, {static:false}) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;
  displayedColumns: string[] = ['employeeId', 'empName','emailId', 'role', 'department', 'salary', 'dateOfJoining', 'project', 'manager', 'action'];
  dataSource!: MatTableDataSource<any>;
  employeeId: any;

  constructor(private empService: EmployeeService,
             public dialog: MatDialog,
             private commonService: CommonService){}

  ngOnInit(): void {
    this.getEmployeeDetails();
    this.getEmployees();
  }

  getEmployeeDetails(){
    this.commonService.getUserdetails().subscribe((response: any) => {
      if (response) {
        this.employeeId = response.employee.employeeId;
      } else {
        this.commonService.findUserAfterPageReload()
      }
    });
  }

  getEmployees(){
    this.empService.getAllEmployees(environment.RESTURL.SERVER+environment.RESTURL.EMPLOYEE).subscribe((response: any) => {
      this.dataSource = new MatTableDataSource(response);
      this.dataSource.paginator = this.paginator;
      this.dataSource.sort = this.sort;
    });
  }

  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();

    if (this.dataSource.paginator) {
      this.dataSource.paginator.firstPage();
    }
  }

  openDialog(element: any): void {
    let dialogRef = this.dialog.open(EmployeeComponent, {
        width:'90%',
        height: '90%',
        autoFocus: false,
        data:  element
    });
    
    dialogRef.afterClosed().subscribe(() => { this.getEmployees(); } );
  }

  openDeleteDialog(element: any){
    let dialogRef = this.dialog.open(DeleteDialogComponent, {
      data:  {'employeeId': element.employeeId, 'component' : 'employee'}
    });

    dialogRef.afterClosed().subscribe(() => { this.getEmployees();} );
  }

}
