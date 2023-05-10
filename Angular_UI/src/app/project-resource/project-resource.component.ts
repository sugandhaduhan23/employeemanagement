import { Component, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { MatPaginator } from '@angular/material/paginator';
import { MatSnackBar } from '@angular/material/snack-bar';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { CommonService } from '../services/common.service';
import { ProjectService } from '../services/project.service';

declare var $:any;

@Component({
  selector: 'app-project-resource',
  templateUrl: './project-resource.component.html',
  styleUrls: ['./project-resource.component.css']
})
export class ProjectResourceComponent implements OnInit {
  managersproject: any;
  teamSize: any;
  projects: any;
  employees: any;
  show: boolean = false;
  resourceMappingForm: any;

  @ViewChild(MatPaginator, { static: false }) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;
  displayedColumns: string[] = ['employeeId', 'empName', 'emailId', 'phoneNo', 'action'];
  dataSource!: MatTableDataSource<any>;

  constructor(private projectService: ProjectService,
    private commonService: CommonService,
    private fb: FormBuilder,
    private snackBar: MatSnackBar) { }

  ngOnInit(): void {
    this.resourceMappingForm = this.fb.group({
      "projectId": ['', Validators.required],
      "teamMembers": [[], Validators.required]
    })

    this.show = false;
    this.getProjects();
    this.getResources();
    this.resourceMappingForm.controls['projectId'].disable();
  }

  getEmployeeDetails(){
    this.commonService.getUserdetails().subscribe((response: any) => {
      if (response) {
        if(response.employee.project){
        this.managersproject = response.employee.project.projectId;
        this.resourceMappingForm.patchValue({
          projectId: this.managersproject
        });
         let obj = this.projects.find((obj: any) =>{
            if( this.managersproject == obj.projectId)
              return obj;
        })
        this.teamSize = obj.teamSize;
        this.fetchTeamMembers();
      }else{
        this.show = true;
      }
      } else {
        this.commonService.findUserAfterPageReload();
      }
    });
  }

  getProjects() {
    this.commonService.getProjects().subscribe((response: any) => {
      this.projects = response;
      this.getEmployeeDetails();
    });
  }

  getResources() {
    this.commonService.getEmployeeByRole(3).subscribe((response: any) => {
      this.employees = response;
    });
  }

  mapResources(form: any) {
    var teamSize = this.resourceMappingForm.controls['teamMembers'].value.length + this.dataSource.data.length;
    if(teamSize > this.teamSize){
      this.showErrorMessage();
    }else{
      this.projectService.mapResource(JSON.stringify(this.resourceMappingForm.getRawValue())).subscribe((response: any) => {
      this.getResources();
      this.fetchTeamMembers();
      form.resetForm();
      this.resourceMappingForm.patchValue({
        projectId: this.managersproject
      });
      this.openSnackBar('Resource(s) mapped successfully!!!')
      });
    }   
  }

  showErrorMessage(){
    $('.alert').removeClass('d-none');
    $('.alert').alert();
    $('.alert').text('Team size cannot be greater than '+this.teamSize);
    $(".alert").fadeTo(5000, 500).slideUp(500, function(){
      $(".alert").slideUp(500);
    });
  }

  unmapResources(data: any) {
    let payload = {
      "projectId": this.resourceMappingForm.controls["projectId"].value,
      "teamMembers": [data.employeeId]
    };
    this.projectService.unmapResource(payload).subscribe((response: any) => {
      this.getResources();
      this.fetchTeamMembers();
      this.openSnackBar('Resource(s) unmapped successfully!!!')
    });
  }

  openSnackBar(message: string) {
    this.snackBar.open(message, undefined, {
      horizontalPosition: 'right',
      verticalPosition: 'top',
      duration: 2000,
    });
  }

  resetForm(form: any) {
    form.resetForm();
    this.resourceMappingForm.reset();
    this.dataSource.data = [];
  }

  fetchTeamMembers() {
    var projectId = this.resourceMappingForm.controls["projectId"].value;
    if (projectId) {
      this.projectService.fetchProjectTeam(projectId).subscribe((response: any) => {
        this.dataSource = new MatTableDataSource(response.teamMembers);
        this.dataSource.paginator = this.paginator;
        this.dataSource.sort = this.sort;
      });
    }
  }

  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();

    if (this.dataSource.paginator) {
      this.dataSource.paginator.firstPage();
    }
  }

}
