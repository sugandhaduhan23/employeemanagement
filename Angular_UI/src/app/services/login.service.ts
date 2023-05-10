import { Injectable } from '@angular/core';
import { HttpService } from './http.service';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class LoginService {
  headers = { 'Content-Type': 'application/json' };

  constructor(private httpService: HttpService) { }

  verifyuser(url: string, data: any) {
    return this.httpService.post(url, data, this.headers);
  }

  updatePassword(url: string, data: any) {
    return this.httpService.put(url, data, this.headers);
  }
}
