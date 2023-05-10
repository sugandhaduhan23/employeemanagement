import { Injectable } from '@angular/core';
import { HttpService } from './http.service';

@Injectable({
  providedIn: 'root'
})
export class EmployeeService {
  headers = {'Content-Type' : 'application/json'};

  constructor(private httpService: HttpService) { }

  getAllEmployees(url: string,){
    return this.httpService.get(url, undefined);
  }

  getEmployeeDetails(url: string, data: number){
    return this.httpService.get(url+"/"+data, undefined);
  }

  addEmployee(url: string, data: any){
    return this.httpService.post(url, data, this.headers);
  }

  updateEmployeeDetails(url: string, data: any){
    let headers = {'Content-Type' : 'application/json'};
    return this.httpService.put(url, data, this.headers);
  }

  deleteEmployee(url: string, data: number){
    return this.httpService.delete(url+"/"+data, undefined);
  }
}
