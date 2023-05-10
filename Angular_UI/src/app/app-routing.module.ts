import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { EmployeeListComponent } from './employee-list/employee-list.component';
import { EmployeeProfileComponent } from './employee-profile/employee-profile.component';
import { EmployeeComponent } from './employee/employee.component';
import { LeaveApproveRejectComponent } from './leave-approve-reject/leave-approve-reject.component';
import { LeaveReportComponent } from './leave-report/leave-report.component';
import { LeaveRequestComponent } from './leave-request/leave-request.component';
import { LoginComponent } from './login/login.component';
import { NotFoundComponent } from './not-found/not-found.component';
import { ProjectReadonlyComponent } from './project-readonly/project-readonly.component';
import { ProjectResourceComponent } from './project-resource/project-resource.component';
import { ProjectComponent } from './project/project.component';

const routes: Routes = [
  { path: 'login', component: LoginComponent },
  { path: 'employee', component: EmployeeComponent },
  { path: 'employeelist', component: EmployeeListComponent },
  { path: 'editProfile', component: EmployeeProfileComponent },
  { path: 'project', component: ProjectComponent },
  { path: 'resources', component: ProjectResourceComponent },
  { path: 'projectdetails', component: ProjectReadonlyComponent },
  { path: 'leaverequest', component: LeaveRequestComponent },
  { path: 'leavereport', component: LeaveReportComponent },
  { path: 'manageleaves', component: LeaveApproveRejectComponent },
  { path: '404', component: NotFoundComponent },
  { path: '**', redirectTo: '/404'},
  {path: '', redirectTo: '/login', pathMatch: 'full'},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
