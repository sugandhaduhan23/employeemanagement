import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { EmployeeService } from '../services/employee.service';
import { environment } from 'src/environments/environment';
import { ProjectService } from '../services/project.service';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-delete-dialog',
  templateUrl: './delete-dialog.component.html',
  styleUrls: ['./delete-dialog.component.css']
})
export class DeleteDialogComponent implements OnInit {

  message!: string;
  action !: any;

  constructor(public dialogRef: MatDialogRef<DeleteDialogComponent>,
              @Inject(MAT_DIALOG_DATA) public data: any,
              private empService: EmployeeService,
              private projectService: ProjectService,
              private snackBar: MatSnackBar,) {}


  ngOnInit(): void {
    switch(this.data.component){
      case 'employee':
            this.message="Are you sure you want to delete the employee with ID: " + this.data.employeeId + " ?";
            this.action = this.deleteEmployee;
            break;
      case 'project':
            this.message="Are you sure you want to delete the project with ID: " + this.data.projectId + " ?";
            this.action = this.deleteProject;
            break;
    }
  }

  deleteEmployee(){
    this.empService.deleteEmployee(environment.RESTURL.SERVER+environment.RESTURL.EMPLOYEE, this.data.employeeId).subscribe(response => {
     this.dialogRef.close();
     this.openSnackBar('Employee deleted successfully!!!');
    });
  }

  deleteProject(){
    this.projectService.deleteProject( this.data.projectId).subscribe(response => {
      this.dialogRef.close();
      this.openSnackBar('Project deleted successfully!!!');
     })
  }

  openSnackBar(message: string) {
    this.snackBar.open(message, undefined, {
      horizontalPosition: 'right',
      verticalPosition: 'top',
      duration: 3000,
    });
  }

}
