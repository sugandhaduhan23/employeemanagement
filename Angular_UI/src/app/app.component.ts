import { Component } from '@angular/core';
import { Router, ResolveEnd } from '@angular/router';
import { Location } from '@angular/common';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'employeemanagement';

  constructor(public router: Router,
              public location: Location){
  }

  ngOnInit(){
    this.isUserLoggedIn();
  }
   
  isUserLoggedIn(){
    if(localStorage.getItem('isUserLoggedIn')!='true'){
          this.router.navigate(['/login']);
    }
    if(this.location.path() == '/login'){
      localStorage.clear();
    }
  }
}
