import { Component, OnInit, ViewChild } from '@angular/core';
import { Form, FormBuilder, Validators } from '@angular/forms';
import { CommonService } from '../services/common.service';
import { ProjectService } from '../services/project.service';
import { MatSnackBar} from '@angular/material/snack-bar';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { MatDialog } from '@angular/material/dialog';
import { DeleteDialogComponent } from '../delete-dialog/delete-dialog.component';
import { ProjectDialogComponent } from '../project-dialog/project-dialog.component';

@Component({
  selector: 'app-project',
  templateUrl: './project.component.html',
  styleUrls: ['./project.component.css']
})
export class ProjectComponent implements OnInit {
  projectForm: any;
  managers: any;
  employeeId: any;

  @ViewChild(MatPaginator, {static:false}) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;
  displayedColumns: string[] = ['projectCode', 'projectName','projectManager', 'teamSize', 'action'];
  dataSource!: MatTableDataSource<any>;

  constructor(private fb: FormBuilder,
              private projectService: ProjectService,
              private commonService: CommonService,
              private snackBar: MatSnackBar,
              public dialog: MatDialog,) { }

  ngOnInit(): void {
    this.getProjects();
    this.getManagers();
    this.getEmployeeDetails();
  }

  getEmployeeDetails(){
    this.commonService.getUserdetails().subscribe((response: any) => {
      if (response) {
        this.employeeId = response.employee.employeeId;
        let projectmanager = response.employee.project ? '' : response.employee.employeeId;
        this.projectForm = this.fb.group({
          "projectCode": ['', Validators.required], 
          "projectName":['', Validators.required],
          "teamSize":['', Validators.required],
          "projectManagerId": [projectmanager, Validators.required]
        });
      } else {
        this.commonService.findUserAfterPageReload()
      }
    });
  }

  getManagers(){
      this.commonService.getEmployeeByRole(2).subscribe((response : any)=>{
        this.managers = response;
      });
  }

  getProjects(){
    this.commonService.getProjects().subscribe((response : any)=>{
      this.dataSource = new MatTableDataSource(response);
      this.dataSource.paginator = this.paginator;
      this.dataSource.sort = this.sort;
    });
  }
  
  createProject(form: any){
    this.projectService.createNewProject(JSON.stringify(this.projectForm.value)).subscribe((response : any)=>{
        this.resetForm(form);
        this.getProjects();
        this.getManagers();
        this.fetchUpdatedData()
        this.openSnackBar('Project created successfully!!!');
    });
  }

  fetchUpdatedData() {
    this.commonService.getuserById().subscribe((response: any) => {
      this.commonService.updatedUserData(response);
    });
  }

  resetForm(form: any){
    form.resetForm();
    this.projectForm.reset();
  }

  openSnackBar(message: string) {
    this.snackBar.open(message, undefined, {
      horizontalPosition: 'right',
      verticalPosition: 'top',
      duration: 2000,
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
    let dialogRef = this.dialog.open(ProjectDialogComponent, {
        autoFocus: false,
        data:  {'data': element, 'managers': this.managers}
    });
    
    dialogRef.afterClosed().subscribe(() => { 
      this.getProjects();
    } );
  }

  openDeleteDialog(element: any){
    let dialogRef = this.dialog.open(DeleteDialogComponent, {
      data:  {'projectId': element.projectId, 'component' : 'project'}
    });

    dialogRef.afterClosed().subscribe(() => { 
      this.getProjects();
    } );
  }

}
