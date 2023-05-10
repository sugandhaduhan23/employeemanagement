import { Component, OnInit, ViewChild } from '@angular/core';
import { FormControl, FormBuilder, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { environment } from 'src/environments/environment';
import { CommonService } from '../services/common.service';
import { EmployeeService } from '../services/employee.service';

@Component({
  selector: 'app-employee-profile',
  templateUrl: './employee-profile.component.html',
  styleUrls: ['./employee-profile.component.css']
})

export class EmployeeProfileComponent implements OnInit {
  employeeForm: any;
  roles: any;
  departments: any;
  gender: any = environment.GENDER;
  formData = false;
  managers: any;
  projects: any;
  @ViewChild('phone') phone!: FormControl;

  constructor(private fb: FormBuilder,
    private commonService: CommonService,
    private empService: EmployeeService,
    private snackBar: MatSnackBar) { }

  ngOnInit(): void {
    this.getEmployeeId();
    this.getRoles()
    this.getDepartments()
    this.getProjects();
    this.getManagers();
  }

  getEmployeeId(): void {
    this.commonService.getUserdetails().subscribe((response: any) => {
      if (response) {
        this.fetchEmployeedetails(response.employee.employeeId);
      } else {
        this.commonService.findUserAfterPageReload()
      }
    });
  }

  setFormData(data: any) {
    this.employeeForm = this.fb.group({
      employeeId: [data.employeeId],
      empName: [data.empName, [Validators.required, Validators.max(50), Validators.maxLength(50)]],
      dob: [data.dob, Validators.required],
      dateOfJoining: [data.dateOfJoining, Validators.required],
      gender: [data.gender, Validators.required],
      fileName: [''],
      photo: [data.photo],
      phoneNo: [data.phoneNo],
      salary: [data.salary, Validators.required],
      emailId: [data.emailId, [Validators.required, Validators.email]],
      roleId: [data.role.roleId, Validators.required],
      departmentId: [data.department ? data.department.departmentId: '', Validators.required],
      projectId: [data.project ? data.project.projectId : ''],
      managerId: [data.manager ? data.manager.employeeId : ''],
      address: this.fb.group({
        addressId: [data.address ? data.address.addressId : ''],
        streetNo: [data.address ? data.address.streetNo : '', Validators.required],
        streetName: [data.address ? data.address.streetName : '', Validators.required],
        cityName: [data.address ? data.address.cityName : '', Validators.required],
        state: [data.address ? data.address.state : '', Validators.required],
        country: [data.address ? data.address.country : '', Validators.required],
        zipCode: [data.address ? data.address.zipCode : '', Validators.required]
      })
    })

    this.employeeForm.controls['projectId'].disable();
    this.employeeForm.controls['managerId'].disable();
    this.employeeForm.controls['dateOfJoining'].disable();
    this.employeeForm.controls['salary'].disable();
    this.employeeForm.controls['emailId'].disable();
    this.employeeForm.controls['roleId'].disable();
    this.employeeForm.controls['departmentId'].disable();

  }

  fetchEmployeedetails(employeeId: any) {
    this.empService.getEmployeeDetails(environment.RESTURL.SERVER + environment.RESTURL.EMPLOYEE, employeeId).subscribe(response => {
      this.setFormData(response);
      this.formData = true;
    });
  }

  getRoles() {
    this.commonService.getRoles().subscribe(response => {
      this.roles = response;
    });
  }

  getDepartments() {
    this.commonService.getDepartments().subscribe(response => {
      this.departments = response;
    });
  }

  getManagers() {
    this.commonService.getEmployeeByRole(2).subscribe((response: any) => {
      this.managers = response;
    });
  }

  getProjects() {
    this.commonService.getProjects().subscribe((response: any) => {
      this.projects = response;
    });
  }

  updateEmployeeDetails(form: any) {
    this.empService.updateEmployeeDetails(environment.RESTURL.SERVER + environment.RESTURL.EMPLOYEE, JSON.stringify(this.employeeForm.getRawValue())).subscribe(response => {
      this.openSnackBar('Details updated successfully!!!')
      this.fetchUpdatedData();
    });
  }

  fetchUpdatedData() {
    this.commonService.getuserById().subscribe((response: any) => {
      this.commonService.updatedUserData(response);
    });
  }

  onFileSelected() {
    let file: any = document.querySelector('#file');
    this.employeeForm.controls['fileName'].setValue(file.files[0].name);

    let f = new Promise((resolve, reject) => {
      let reader = new FileReader();
      reader.readAsDataURL(file.files[0]);
      reader.onload = () => {
        resolve(reader.result);
      };
      reader.onerror = reject;
    })

    f.then(data => this.employeeForm.controls['photo'].setValue(data));
  }

  openSnackBar(message: string) {
    this.snackBar.open(message, undefined, {
      horizontalPosition: 'right',
      verticalPosition: 'top',
      duration: 2000,
    });
  }
}
