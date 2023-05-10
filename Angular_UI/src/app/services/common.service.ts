import { Injectable } from '@angular/core';
import { HttpService } from './http.service';
import { environment } from 'src/environments/environment';
import { BehaviorSubject, EMPTY, Observable, Subject, Subscriber } from 'rxjs';

@Injectable({
  providedIn: 'root'
})

export class CommonService {
  private userData = new BehaviorSubject<any>(null);
  private userData1 = new Subject();
  private userDetails = this.userData.asObservable();

  constructor(private httpService: HttpService) { }

  returnSubject(): Observable<any>{
    return this.userData1;
  }

  updatedUserData(data: any) {
    this.userData.next(data);
    this.userData1.next(data);
  }

  getUserdetails() {
    return this.userDetails;
  }

  getRoles() {
    return this.httpService.get(environment.RESTURL.SERVER + environment.RESTURL.ROLES, undefined);
  }

  getDepartments() {
    return this.httpService.get(environment.RESTURL.SERVER + environment.RESTURL.DEPARTMENTS, undefined);
  }

  getLeaveTypes() {
    return this.httpService.get(environment.RESTURL.SERVER + environment.RESTURL.LEAVE_TYPE, undefined);
  }

  getEmployeeByRole(roleId: number) {
    return this.httpService.get(environment.RESTURL.SERVER + environment.RESTURL.EMPLOYEE_ROLE + roleId, undefined);
  }

  getEmployeeNames(roleId: number) {
    return this.httpService.get(environment.RESTURL.SERVER + environment.RESTURL.EMPLOYEE_NAMES, undefined);
  }

  getuserById() {
    if(localStorage.getItem("userid"))
       return this.httpService.get(environment.RESTURL.SERVER + environment.RESTURL.LOGIN + "/" + localStorage.getItem("userid"), undefined);
    return EMPTY;
  }

  findUserAfterPageReload() {
    this.getuserById().subscribe((response: any) => {
      this.updatedUserData(response);
    });
  }

  getProjects(){
    return this.httpService.get(environment.RESTURL.SERVER+environment.RESTURL.PROJECT, undefined);
  }


}
