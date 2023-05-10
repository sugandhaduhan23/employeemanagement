import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {
  time : string = '';
  name: string = 'Sugandha';
  interval: any;


  constructor(public router: Router) { }

  ngOnInit(): void {
    this.clock();
    this.interval = setInterval(() => this.clock(), 1000);
  }

  clock(){
    let date = new Date();
    let hours = date.getHours().toString();; // 0 - 23
    let minutes = date.getMinutes().toString();; // 0 - 59
    let seconds = date.getSeconds().toString();; // 0 - 59

    if (hours.length < 2) {
      hours = '0' + hours;
    }
  
    if (minutes.length < 2) {
      minutes = '0' + minutes;
    }
  
    if (seconds.length < 2) {
      seconds = '0' + seconds;
    }
  
    this.time = hours + ':' + minutes + ':' + seconds;
  }

  logout(){
    localStorage.clear();
  }
}
