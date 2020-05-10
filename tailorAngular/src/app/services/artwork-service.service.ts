import { Injectable } from '@angular/core';
import { SessionServiceService } from './session-service.service';

import {HttpHeaders} from '@angular/common/http';
import {HttpClient} from '@angular/common/http';
import { Observable, throwError} from 'rxjs';
import { catchError } from 'rxjs/operators';

import { Artwork } from '../class/artwork';
import { HttpErrorResponse } from '@angular/common/http';

const httpOptions = {
  headers : new HttpHeaders({'Content-Type' : 'application/json'})
}
@Injectable({
  providedIn: 'root'
})
export class ArtworkServiceService {

  baseUrl : string = '/api/Artwork';

  artworks: Artwork[];

  constructor(private httpClient: HttpClient) {

  }

  getArtworks() {
    return this.httpClient.get<any>(this.baseUrl + "/retrieveAllArtworks").pipe();
    {
      catchError(this.handleError);
    }
  }

  getViewArtwork(artworkId: number) : Observable<any>{
    return this.httpClient.get<any>(this.baseUrl + "/retrieveArtworkById/" + artworkId).pipe();
    {
      catchError(this.handleError);
    }

  }

  getFilteredArtwork(tagId: number): Observable<any> {
		return this.httpClient.get<any>("http://localhost:8080/tailoredJsfRws/Resources/Customer/filterArtworkByTagId/" + tagId).pipe
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
