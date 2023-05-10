import { Component, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { MatPaginator } from '@angular/material/paginator';
import { MatSnackBar } from '@angular/material/snack-bar';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { CommonService } from '../services/common.service';
import { LeaveService } from '../services/leave.service';

@Component({
  selector: 'app-leave-request',
  templateUrl: './leave-request.component.html',
  styleUrls: ['./leave-request.component.css']
})
export class LeaveRequestComponent implements OnInit {
  leaveRequestForm: any;
  leaveBalance!: MatTableDataSource<any>;
  dataSource!: MatTableDataSource<any>;;
  leaveType: any;
  employeeId: any;
  displayedColumns: string[] = ['leaveType', 'leavesAllowed', 'leaveBalance'];
  leaveTableColumns: string[] = ['leaveType', 'leaveReason', 'leaveFrom', 'leaveTo', 'status', 'action'];
  @ViewChild(MatPaginator, {static:false}) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;


  constructor(private leaveService: LeaveService,
              private fb: FormBuilder,
              private snackBar: MatSnackBar,
              private commonService: CommonService) { }

  ngOnInit(): void {
    this.leaveRequestForm = this.fb.group({
      "employeeId": [''],
      "leaveTypeId": ['', Validators.required],
      "leaveReason": ['', Validators.required],
      "leaveFrom": ['', Validators.required],
      "leaveTo": ['', Validators.required],
      "leaveCount": ['']
    });
    this.getLeaveType();
    this.getEmployeeDetails();
  }

  getEmployeeDetails(){
    this.commonService.getUserdetails().subscribe((response: any) => {
      if (response) {
        this.employeeId = response.employee.employeeId;
        this.getLeaveBalance();
        this.getEmployeeLeaveRequests();
        this.leaveRequestForm.patchValue({
          "employeeId":  this.employeeId 
        });
      } else {
        this.commonService.findUserAfterPageReload();
      }
    });
  }

  getLeaveType() {
    this.leaveService.getLeaveTypes().subscribe((response: any) => {
      this.leaveType = response;
    })
  }

  getLeaveBalance() {
    this.leaveService.getLeaveBalance(this.employeeId).subscribe((response: any) => {
      this.leaveBalance = new MatTableDataSource(response);
    })
  }

  getEmployeeLeaveRequests(){
    this.leaveService.getEmployeeLeaveRequest(this.employeeId).subscribe((response: any) => {
      this.dataSource = new MatTableDataSource(response);
      this.dataSource.paginator = this.paginator;
      this.dataSource.sort = this.sort;
    })
  }

  withdrawLeaveRequest(data: any) {
    this.leaveService.withdrawLeaveRequest(data.id).subscribe((response: any) => {
      this.getEmployeeLeaveRequests();
      this.getLeaveBalance();
      this.openSnackBar('Leave request withdrawn successfully!!!');
    })
  }

  sendLeaveRequest(form: any) {
    let leave = this.leaveBalance.data.find((obj: any) => obj.leave.leaveTypeId == this.leaveRequestForm.value.leaveTypeId);
    let leaveCount = ((this.leaveRequestForm.value.leaveTo - this.leaveRequestForm.value.leaveFrom) / (1000 * 60 * 60 * 24)) + 1;
    if (leaveCount > leave.leaveBalance)
      this.openSnackBar('Not enough ' + leave.leave.leaveType) + '.';
    else {
      this.leaveRequestForm.controls['leaveCount'].setValue(leaveCount);
      this.leaveService.saveLeaverequest(JSON.stringify(this.leaveRequestForm.value)).subscribe((response: any) => {
        this.getLeaveBalance();
        this.getEmployeeLeaveRequests();
        this.openSnackBar('Leave request sent successfully!!!');
        this.resetForm(form);
      })
    }
  }
  
  openSnackBar(message: string) {
    this.snackBar.open(message, undefined, {
      horizontalPosition: 'right',
      verticalPosition: 'top',
      duration: 3000,
    });
  }

  resetForm(form: any) {
    form.resetForm();
    this.leaveRequestForm.reset();
    this.leaveRequestForm.patchValue({
      "employeeId":  this.employeeId 
    });
  }
}
