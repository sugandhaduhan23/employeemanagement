import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { map, catchError, } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class HttpService {

  constructor(private http: HttpClient) {
  }

  get<T>(url: string, headerObject?: any): Observable<HttpResponse<any>> {
    return this.http.get<any>(url, { headers: headerObject })
              .pipe(map(response => {
                       return response;
                    }));
  }

  post<T>(url: string, data: any,headerObject?: any): Observable<HttpResponse<any>> {
    return this.http.post<any>(url, data, { headers: headerObject })
              .pipe(map(response => {
                       return response;
                    }));
  }

  put<T>(url: string, data: any,headerObject?: any): Observable<HttpResponse<any>> {
    return this.http.put<any>(url, data, { headers: headerObject })
              .pipe(map(response => {
                       return response;
                    }));
  }

  delete<T>(url: string,headerObject?: any): Observable<HttpResponse<any>> {
    return this.http.delete<any>(url, { headers: headerObject })
              .pipe(map(response => {
                       return response;
                    }));
  }
}
