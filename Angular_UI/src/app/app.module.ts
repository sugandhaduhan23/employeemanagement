import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { EmployeeComponent } from './employee/employee.component';
import { HeaderComponent } from './header/header.component';
import { FooterComponent } from './footer/footer.component';
import { NavbarComponent } from './navbar/navbar.component';
import { LoginComponent } from './login/login.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { NgMaterialModule } from './ng-material/ng-material.module';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { HttpInterceptorInterceptor } from './helpers/http-interceptor.interceptor';
import { NotFoundComponent } from './not-found/not-found.component';
import { NgxMatIntlTelInputModule } from 'ngx-11-mat-intl-tel-input';
import { EmployeeListComponent } from './employee-list/employee-list.component';
import { DeleteDialogComponent } from './delete-dialog/delete-dialog.component';
import { ProjectComponent } from './project/project.component';
import { ProjectDialogComponent } from './project-dialog/project-dialog.component';
import { ProjectResourceComponent } from './project-resource/project-resource.component';
import { ProjectReadonlyComponent } from './project-readonly/project-readonly.component';
import { LeaveRequestComponent } from './leave-request/leave-request.component';
import { LeaveReportComponent } from './leave-report/leave-report.component';
import { LeaveApproveRejectComponent } from './leave-approve-reject/leave-approve-reject.component';
import { EmployeeProfileComponent } from './employee-profile/employee-profile.component';

@NgModule({
  declarations: [
    AppComponent,
    EmployeeComponent,
    HeaderComponent,
    FooterComponent,
    NavbarComponent,
    LoginComponent,
    NotFoundComponent,
    EmployeeListComponent,
    DeleteDialogComponent,
    ProjectComponent,
    ProjectDialogComponent,
    ProjectResourceComponent,
    ProjectReadonlyComponent,
    LeaveRequestComponent,
    LeaveReportComponent,
    LeaveApproveRejectComponent,
    EmployeeProfileComponent
  ],
  imports: [
    BrowserModule,
    FormsModule, 
    ReactiveFormsModule,  
    AppRoutingModule,
    NgMaterialModule,
    BrowserAnimationsModule,
    HttpClientModule,  
    NgxMatIntlTelInputModule
  ],
  providers: [
    {
      provide: HTTP_INTERCEPTORS,
      useClass: HttpInterceptorInterceptor,
      multi: true
    }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
