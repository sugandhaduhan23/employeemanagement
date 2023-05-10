import { Component, OnInit, ViewChild } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { MatTabChangeEvent } from '@angular/material/tabs';
import { CommonService } from '../services/common.service';
import { LeaveService } from '../services/leave.service';

@Component({
  selector: 'app-leave-approve-reject',
  templateUrl: './leave-approve-reject.component.html',
  styleUrls: ['./leave-approve-reject.component.css']
})
export class LeaveApproveRejectComponent implements OnInit {

  data: any;

  @ViewChild(MatPaginator, {static:false}) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;
  
  dataSource!: MatTableDataSource<any>;;
  displayedColumns: string[] = ['employeeId', 'empName', 'leaveType', 'leaveReason', 'leaveFrom', 'leaveTo', 'status', 'action'];
 
  constructor(private leaveService: LeaveService,
              private commonService: CommonService) { }

  ngOnInit(): void {
    this.fetchUserDetails();
  }

  fetchUserDetails() {
    this.commonService.getUserdetails().subscribe((response: any) => {
      if (response) {
        this.data = response.employee;
        this.getLeaveRequests();
      } else {
        this.commonService.findUserAfterPageReload()
      }
    });
  }

  getLeaveRequests(tabChangeEvent?: MatTabChangeEvent){
    let status = 0;
    if(tabChangeEvent)
      status = tabChangeEvent.index + 1;
    else
      status = 1;
    this.leaveService.fetchLeaveRequestsforApproval(this.data.role.roleId, this.data.project ? this.data.project.projectId : 0 , status).subscribe((response: any) => {
      this.dataSource = new MatTableDataSource(response);
      this.dataSource.paginator = this.paginator;
      this.dataSource.sort = this.sort;
    })
  }

  approveRejectRequest(id: any, status: number){
    let payload ={
      "id": id,
      "status": status
    }
    this.leaveService.approveRejectLeaves(payload).subscribe((response: any) => {
       this.getLeaveRequests();
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
