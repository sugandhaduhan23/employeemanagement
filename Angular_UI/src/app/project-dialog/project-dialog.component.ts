import { Component, Inject, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ProjectService } from '../services/project.service';

@Component({
  selector: 'app-project-dialog',
  templateUrl: './project-dialog.component.html',
  styleUrls: ['./project-dialog.component.css']
})
export class ProjectDialogComponent implements OnInit {
  projectForm!: any;
  managers: any;


  constructor(public dialogRef: MatDialogRef<ProjectDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any,
    private snackBar: MatSnackBar,
    private fb: FormBuilder,
    private projectService: ProjectService) { }

  ngOnInit(): void {
    this.projectForm = this.fb.group({
      "projectId": [this.data.data.projectId, Validators.required],
      "projectCode": [this.data.data.projectCode, Validators.required],
      "projectName": [this.data.data.projectName, Validators.required],
      "teamSize": [this.data.data.teamSize, Validators.required],
      "projectManagerId": [this.data.data.projectManager.employeeId, Validators.required]
    });
    this.managers = this.data.managers;
  }

  updateProject() {
    this.projectService.updateProject(JSON.stringify(this.projectForm.value)).subscribe((response: any) => {
      this.openSnackBar('Project updated successfully!!!');
      this.dialogRef.close();
    });
  }

  openSnackBar(message: string) {
    this.snackBar.open(message, undefined, {
      horizontalPosition: 'right',
      verticalPosition: 'top',
      duration: 3000,
    });
  }

}
