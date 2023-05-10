import { SelectionModel } from '@angular/cdk/collections';
import { Component, OnInit, ViewChild } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { CommonService } from '../services/common.service';
import { ProjectService } from '../services/project.service';

@Component({
  selector: 'app-project-readonly',
  templateUrl: './project-readonly.component.html',
  styleUrls: ['./project-readonly.component.css']
})
export class ProjectReadonlyComponent implements OnInit {
  project: any;
  data: any;
  show: boolean = false;
  
  @ViewChild(MatPaginator, { static: false }) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;
  displayedColumns: string[] = ['employeeId', 'empName', 'emailId', 'phoneNo'];
  dataSource!: MatTableDataSource<any>;

  constructor(private projectService: ProjectService,
    private commonService: CommonService) { }

  ngOnInit(): void {
    this.show = false;
    this.fetchUserDetails();
  }

  fetchUserDetails() {
    this.commonService.getUserdetails().subscribe((response: any) => {
      if (response) {
        this.project = response.employee.project;
        this.fetchTeamMembers()
      } else {
        this.commonService.findUserAfterPageReload()
      }
    });
  }

  fetchTeamMembers() {
    if (this.project) {
      this.projectService.fetchProjectTeam(this.project.projectId).subscribe((response: any) => {
        if(response){
          this.data = response;
          this.dataSource = new MatTableDataSource(response.teamMembers);
          this.dataSource.paginator = this.paginator;
          this.dataSource.sort = this.sort;
        }
      });
    }
    else{
      this.show = true;
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
