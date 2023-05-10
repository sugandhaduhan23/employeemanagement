import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { CommonService } from '../services/common.service';
import { LoginService } from '../services/login.service';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit {
  username !: string;
  photo!: any;
  role: any;
  engineer: boolean =  false;
  manager: boolean =  false;
  hr: boolean = false;

  constructor(private commonService: CommonService,
              public router: Router) { }

  ngOnInit(): void {
    this.commonService.returnSubject().subscribe((response: any) => {
      if (response) {
        this.username = response.employee.empName;
        this.photo = response.employee.photo ? response.employee.photo: '../assets/images/avatar.png' ;
        this.role = response.employee.role.roleId;
        this.setAuthorization();
      } else {
        this.commonService.findUserAfterPageReload()
      }
    });



    // this.commonService.getUserdetails().subscribe((response: any) => {
    //   if (response) {
    //     this.username = response.employee.empName;
    //     this.photo = response.employee.photo ? response.employee.photo: '../assets/images/avatar.png' ;
    //     this.role = response.employee.role.roleId;
    //     this.setAuthorization();
    //   } else {
    //     this.commonService.findUserAfterPageReload()
    //   }
    // });
  }

  setAuthorization(){
    if(this.role == 2){
      this.manager = true
      this.engineer = false
      this.hr = false
    }else if(this.role == 3){
      this.manager = false
      this.engineer = true
      this.hr = false
    }else if(this.role == 4){
      this.manager = false
      this.engineer = false
      this.hr = true
    }else{
      this.manager = true
      this.engineer = true
      this.hr = true
    }
  }
}
