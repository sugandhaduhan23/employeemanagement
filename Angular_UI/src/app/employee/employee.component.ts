import { Component, Inject, OnInit, Optional, ViewChild } from '@angular/core';
import { FormBuilder, FormControl, Validators } from '@angular/forms';
import { CommonService } from '../services/common.service';
import { environment } from 'src/environments/environment';
import { EmployeeService } from '../services/employee.service';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-employee',
  templateUrl: './employee.component.html',
  styleUrls: ['./employee.component.css']
})

export class EmployeeComponent implements OnInit {
  employeeForm: any;
  roles: any;
  departments: any;
  gender: any = environment.GENDER;
  showButton: boolean = true;
  title!: string;
  managers: any;
  projects: any;
  @ViewChild('phone') phone!: FormControl;

  constructor(private fb: FormBuilder,
    private commonService: CommonService,
    private empService: EmployeeService,
    private snackBar: MatSnackBar,
    public dialogRef: MatDialogRef<EmployeeComponent>,
    @Optional() @Inject(MAT_DIALOG_DATA) public data: any) { }

  ngOnInit(): void {
    this.getEmployeeDetails()
    this.getManagers();
    this.getProjects();
    this.getRoles()
    this.getDepartments()
  }

  getEmployeeDetails(){
    this.commonService.getUserdetails().subscribe((response: any) => {
      if (response) {
        this.setFormValues(response.employee.employeeId);
      } else {
        this.commonService.findUserAfterPageReload()
      }
    });
  }

  setFormValues(empid: number){
    if (this.data) {
      this.employeeForm = this.fb.group({
        employeeId: [this.data.employeeId],
        empName: [this.data.empName, [Validators.required, Validators.max(50), Validators.maxLength(50)]],
        dob: [this.data.dob, Validators.required],
        dateOfJoining: [this.data.dateOfJoining, Validators.required],
        gender: [this.data.gender, Validators.required],
        fileName: [],
        photo: [this.data.photo],
        phoneNo: [this.data.phoneNo],
        salary: [this.data.salary, Validators.required],
        emailId: [this.data.emailId, [Validators.required, Validators.email]],
        roleId: [this.data.role.roleId, Validators.required],
        departmentId: [this.data.department.departmentId, Validators.required],
        projectId: [this.data.project ? this.data.project.projectId : ''],
        managerId: [this.data.manager ? this.data.manager.employeeId : ''],
        address: this.fb.group({
          addressId: [this.data.address.addressId],
          streetNo: [this.data.address.streetNo, Validators.required],
          streetName: [this.data.address.streetName, Validators.required],
          cityName: [this.data.address.cityName, Validators.required],
          state: [this.data.address.state, Validators.required],
          country: [this.data.address.country, Validators.required],
          zipCode: [this.data.address.zipCode, Validators.required]
        })
      })
      this.showButton = false;
      this.title = "UPDATE EMPLOYEE DETAILS";
      if(empid == this.data.employeeId){
        this.employeeForm.controls['projectId'].disable();
        this.employeeForm.controls['managerId'].disable();
        this.employeeForm.controls['dateOfJoining'].disable();
        this.employeeForm.controls['salary'].disable();
        this.employeeForm.controls['emailId'].disable();
        this.employeeForm.controls['roleId'].disable();
        this.employeeForm.controls['departmentId'].disable();
      }else if(!this.data.project){
        this.employeeForm.controls['projectId'].disable();
        this.employeeForm.controls['managerId'].disable();
      }else{
        this.employeeForm.controls['managerId'].disable();
      }
    }
    else {
      this.employeeForm = this.fb.group({
        employeeId: [''],
        empName: ['', [Validators.required, Validators.max(50), Validators.maxLength(50)]],
        dob: ['', Validators.required],
        dateOfJoining: ['', Validators.required],
        gender: ['', Validators.required],
        fileName: [''],
        photo: [''],
        phoneNo: [''],
        salary: ['', Validators.required],
        emailId: ['', [Validators.required, Validators.email]],
        roleId: ['', Validators.required],
        departmentId: ['', Validators.required],
        projectId: [""],
        managerId: [""],
        address: this.fb.group({
          addressId: [''],
          streetNo: ['', Validators.required],
          streetName: ['', Validators.required],
          cityName: ['', Validators.required],
          state: ['', Validators.required],
          country: ['', Validators.required],
          zipCode: ['', Validators.required]
        })
      })
      this.title = "ADD NEW EMPLOYEE";
      this.employeeForm.controls['projectId'].disable();
      this.employeeForm.controls['managerId'].disable();
    }
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

  addEmployee(form: any) {
    this.empService.addEmployee(environment.RESTURL.SERVER + environment.RESTURL.EMPLOYEE, JSON.stringify(this.employeeForm.getRawValue())).subscribe(response => {
      this.resetForm(form);
      this.openSnackBar('Employee added successfully!!!')
    });
  }

  updateEmployeeDetails(form: any) {
    this.empService.updateEmployeeDetails(environment.RESTURL.SERVER + environment.RESTURL.EMPLOYEE, JSON.stringify(this.employeeForm.getRawValue())).subscribe(response => {
      this.fetchUpdatedData();
      this.openSnackBar('Employee details updated successfully!!!')
      this.dialogRef.close();
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

  resetForm(form: any) {
    form.resetForm();
    this.employeeForm.reset();
    this.phone.reset();
  }

  openSnackBar(message: string) {
    this.snackBar.open(message, undefined, {
      horizontalPosition: 'right',
      verticalPosition: 'top',
      duration: 2000,
    });
  }

  emptyManager(){
    this.employeeForm.patchValue({
      "managerId": ''
    });
  }

}
