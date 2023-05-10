import { Injectable } from '@angular/core';
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor,
  HttpResponse,
  HttpErrorResponse
} from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { retry, catchError } from 'rxjs/operators';
import { CommonService } from '../services/common.service';
import { MatSnackBar } from '@angular/material/snack-bar';

declare var $:any;

@Injectable()
export class HttpInterceptorInterceptor implements HttpInterceptor {

  constructor(private commonService: CommonService,
    private snackBar: MatSnackBar) {}

  intercept(request: HttpRequest<unknown>, next: HttpHandler): Observable<HttpEvent<unknown>> {

      return next.handle(request)
        .pipe(
        catchError((error: HttpErrorResponse) => {
            let errorMessage = '';
            if (error.error instanceof ErrorEvent) {
              // client-side error
                errorMessage = error.error.message;
            } else {
              // server-side error
                errorMessage = 'Error Code:'+ error.status +'\nMessage:'+ error.error.message;
            }
            $('.alert').removeClass('d-none');
            $('.alert').alert();
            $('.alert').text(errorMessage);
            $(".alert").fadeTo(5000, 500).slideUp(500, function(){
            $(".alert").slideUp(500);
            });
            return throwError(errorMessage);
          })
        ) 
  }
}
  
