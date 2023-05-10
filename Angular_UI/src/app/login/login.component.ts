import { Component, OnInit, ViewChild } from '@angular/core';
import { Router } from '@angular/router';
import { FormBuilder, NgForm } from '@angular/forms';
import { Validators } from '@angular/forms';
import { LoginService } from '../services/login.service';
import { environment } from 'src/environments/environment';
import { CommonService } from '../services/common.service';

declare var $:any;

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  loginForm : any;
  resetPasswordForm : any;
  resetPassword : boolean = false;
  btnName: string | undefined;
  @ViewChild('loginFormDirective') loginFormDirective: any;
  @ViewChild('resetFormDirective') resetFormDirective: any;

 constructor(private router: Router, private fb : FormBuilder,
              private loginService : LoginService,
              private commonService: CommonService) {}

  ngOnInit(): void {
    $('.alert').hide();
    this.loginForm = this.fb.group({
      email: ['', [Validators.required, Validators.email]],
      password: ['', Validators.required]
    })

    this.resetPasswordForm = this.fb.group({
      userId: [null],
      email: ['', [Validators.required, Validators.email]],
      password: ['', Validators.required]
    })
  }

  login(){
     this.loginService.verifyuser(environment.RESTURL.SERVER+environment.RESTURL.LOGIN, JSON.stringify(this.loginForm.value)).subscribe((response : any) => {
      if(response.data.firstTimeLogin){
        this.resetPasswordForm.setValue({
          userId: response.data.userId,
          email: response.data.email,
          password: ''
        });
        this.resetPasswordForm.controls['email'].disable();
        this.resetPassword = true;
        this.btnName = 'Update Password';
        (<any>$('.carousel')).carousel(1);
      }else{
       this.router.navigate(['/employee']);
       localStorage.setItem('isUserLoggedIn', 'true');
       localStorage.setItem('userid', response.data.userId);
      }
      this.commonService.updatedUserData(response.data);
    });
  }

  updatePassword(){
    this.loginService.updatePassword(environment.RESTURL.SERVER+environment.RESTURL.LOGIN, JSON.stringify(this.resetPasswordForm.value)).subscribe(response => {
      (<any>$('.carousel')).carousel(0);
      this.resetPasswordForm.reset();
      this.resetFormDirective.resetForm();
    });
  }

  forgotPassword(){
   $('.alert').hide();
   (<any>$('.carousel')).carousel(1);
   this.btnName = 'Reset Password';
   this.resetPassword = true;
   this.resetPasswordForm.controls['email'].enable();
   this.loginForm.reset();
   this.loginFormDirective.resetForm();
  }

  back(){
    $('.alert').hide();
    (<any>$('.carousel')).carousel(0);
    this.resetPasswordForm.reset();
    this.resetFormDirective.resetForm();
  }

}


