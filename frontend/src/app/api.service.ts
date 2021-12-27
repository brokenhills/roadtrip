import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from  '@angular/common/http';
import { environment } from '../environments/environment';
import { map, catchError } from 'rxjs/operators';
import { Observable, throwError  } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ApiService {

  API_URL = environment.apiUrl;

  HEADERS: HttpHeaders = new HttpHeaders({
    'Access-Control-Allow-Origin': '*'
  });

  constructor(private httpClient: HttpClient) { }

  private handleError(error: Response | any) {
    console.log(error);
    return throwError(error);
  }

  public login(data: {}): Observable<any> {
    let headers = new HttpHeaders({
      'Access-Control-Allow-Origin': '*'
    });
    return this.httpClient.post(`${this.API_URL}/auth/login`, data, {headers: headers}).pipe(
      map((response: Response | any) => {
        return response;
      }),
      catchError(this.handleError)
    );
  }

  public getRecentWorkflows(): Observable<any>  {
    return this.httpClient
    .get(`${this.API_URL}/workflows/recent`).pipe(
      map((response: any) => {
        return response;
      }),
      catchError(this.handleError)
    );
  }

  public getWorkflows(): Observable<any>  {
    let headers = new HttpHeaders({
      'Access-Control-Allow-Origin': 'https://localhost:4200'
    });
    return this.httpClient
    .get(`${this.API_URL}/workflows`, {headers}).pipe(
      map((response: any) => {
        return response;
      }),
      catchError(this.handleError)
    );
  }

  public getAny(url: string): Observable<any> {
    let headers = new HttpHeaders({
      'Access-Control-Allow-Origin': 'https://localhost:4200'
    });
    return this.httpClient
    .get(url).pipe(
      map((response: any) => {
        return response;
      }),
      catchError(this.handleError)
    );
  }

  public putAny(url: string, body: any): Observable<any> {
    let headers = new HttpHeaders({
      'Access-Control-Allow-Origin': 'https://localhost:4200'
    });
    return this.httpClient
    .put(url, body).pipe(
      map((response: any) => {
        return response;
      }),
      catchError(this.handleError)
    );
  }

  public createWorkflow(body: any): Observable<any> {
    let headers = new HttpHeaders({
      'Access-Control-Allow-Origin': 'https://localhost:4200'
    });
    return this.httpClient
    .post(`${this.API_URL}/workflows`, body, {headers: headers}).pipe(
      map((response: any) => {
        return response;
      }),
      catchError(this.handleError)
    );
  }

  public getUser(username: string): Observable<any> {
    let headers = new HttpHeaders({
      'Access-Control-Allow-Origin': 'https://localhost:4200'
    });
    return this.httpClient
    .get(`${this.API_URL}/users/search/findByUsername?username=${username}`).pipe(
      map((response: any) => {
        return response;
      }),
      catchError(this.handleError)
    );
  }

}
