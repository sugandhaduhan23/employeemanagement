import { Component, OnInit, ViewChild } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { CommonService } from '../services/common.service';
import { LeaveService } from '../services/leave.service';

@Component({
  selector: 'app-leave-report',
  templateUrl: './leave-report.component.html',
  styleUrls: ['./leave-report.component.css']
})
export class LeaveReportComponent implements OnInit {
  @ViewChild(MatPaginator, {static:false}) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;
  
  dataSource!: MatTableDataSource<any>;;
  displayedColumns: string[] = ['employeeId', 'empName', 'leaveType', 'leaveReason', 'leaveFrom', 'leaveTo', 'status'];
 
  constructor(private leaveService: LeaveService) { }

  ngOnInit(): void {
    this.getLeaveRequests();
  }

  getLeaveRequests(){
    this.leaveService.getLeaveRequests().subscribe((response: any) => {
      this.dataSource = new MatTableDataSource(response);
      this.dataSource.paginator = this.paginator;
      this.dataSource.sort = this.sort;
    })
  }

  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();

    if (this.dataSource.paginator) {
      this.dataSource.paginator.firstPage();
    }
  }

}
