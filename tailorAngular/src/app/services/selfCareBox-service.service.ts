import { Injectable } from '@angular/core';
import { SessionServiceService } from './session-service.service';

import {HttpHeaders} from '@angular/common/http';
import {HttpClient} from '@angular/common/http';
import { Observable, throwError} from 'rxjs';
import { catchError } from 'rxjs/operators';

import { SelfCareBox } from '../class/selfCareBox';
import { HttpErrorResponse } from '@angular/common/http';

const httpOptions = {
  headers : new HttpHeaders({'Content-Type' : 'application/json'})
}
@Injectable({
  providedIn: 'root'
})
export class SelfCareBoxServiceService {

  baseUrl : string = '/api/SelfCareBox';

  selfCareBoxes: SelfCareBox[];

  constructor(private httpClient: HttpClient) {

  }

  getSelfCareBoxes() {
    return this.httpClient.get<any>(this.baseUrl + "/retrieveAllSelfCareBoxes").pipe();
    {
      catchError(this.handleError);
    }
  }
  
  getViewSelfCareBox(selfCareBoxId: number) : Observable<any>{
    return this.httpClient.get<any>(this.baseUrl + "/retrieveSelfCareBoxById/" + selfCareBoxId).pipe();
    {
      catchError(this.handleError);
    }

  }

  getFilteredSelfCareBox(tagId: number): Observable<any> {
		return this.httpClient.get<any>("http://localhost:8080/tailoredJsfRws/Resources/Customer/filterSelfCareBoxByTagId/" + tagId).pipe
			(
				catchError(this.handleError)
			);
  }
  
  private handleError(error: HttpErrorResponse){
    let errorMessage : string = '';
    if(error.error instanceof ErrorEvent){
      errorMessage = 'An unknown error has occurred: ' + error.error.message;
    }
    else{
      errorMessage = "An HTTP error has occurred: " + `HTTP ${error.status}:${error.error.message}`;
    }
    return throwError(errorMessage);
  }
} 
