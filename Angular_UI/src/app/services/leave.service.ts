import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { HttpService } from './http.service';

@Injectable({
  providedIn: 'root'
})
export class LeaveService {
  headers = {'Content-Type' : 'application/json'};

  constructor(private httpService: HttpService) { }

  getLeaveTypes(){
    return this.httpService.get(environment.RESTURL.SERVER+environment.RESTURL.LEAVE_TYPE, undefined);
  }

  getLeaveBalance(userId: any){
    return this.httpService.get(environment.RESTURL.SERVER+environment.RESTURL.LEAVE_BALANCE+userId, undefined);
  }

  getLeaveRequests(){
    return this.httpService.get(environment.RESTURL.SERVER+environment.RESTURL.REQUEST_LEAVE, undefined);
  }

  getEmployeeLeaveRequest(userId: any){
    return this.httpService.get(environment.RESTURL.SERVER+environment.RESTURL.REQUEST_LEAVE+"/"+userId, undefined);
  }

  saveLeaverequest(data: any){
    return this.httpService.post(environment.RESTURL.SERVER+environment.RESTURL.REQUEST_LEAVE, data, this.headers);
  }

  withdrawLeaveRequest(id: any){
    return this.httpService.delete(environment.RESTURL.SERVER+environment.RESTURL.REQUEST_LEAVE+"/"+id, undefined);
  }

  fetchLeaveRequestsforApproval(role: number, project: number, status: number){
    return this.httpService.get(environment.RESTURL.SERVER+environment.RESTURL.REQUEST_LEAVE+"/"+role+"/"+project+"/"+status, undefined);
  }

  approveRejectLeaves(data:any){
    return this.httpService.put(environment.RESTURL.SERVER+environment.RESTURL.APPROVE_REJECT_LEAVE, data, this.headers);
  }
}