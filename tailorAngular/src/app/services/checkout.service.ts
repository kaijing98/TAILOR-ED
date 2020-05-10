import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';

import {SessionServiceService} from './session-service.service';

import { CreateArtworkOrderReq } from '../class/create-artwork-order-req';
import { CreateSelfCareBoxOrderReq } from '../class/create-selfCareBox-order-req';

const httpOptions = {
	headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable({
  providedIn: 'root'
})
export class CheckoutService {

  baseUrl: string = 'http://localhost:8080/tailoredJsfRws/Resources/Checkout';
	
	
	
	constructor(private httpClient: HttpClient,
				private sessionService: SessionServiceService)
	{		}
  
  createArtworkOrder(artworkID : number, artworkPriceID : number, customerID : number, quantity: number) :Observable<any>{
    let createNewArtworkOrder = {
      "artworkID" : artworkID,
      "artworkPriceID" : artworkPriceID,
      "customerID" : customerID,
      "quantity" : quantity
	};
	
    return this.httpClient.put<any>(this.baseUrl + "/createArtworkOrder/",createNewArtworkOrder,httpOptions).pipe(
      catchError(this.handleError)
    );
  }

  createSelfCareBoxOrder(selfCareBoxID : number, boxDiscountID : number, customerID : number, quantity: number) :Observable<any>{
    let createNewSelfCareBoxOrder = {
      "selfCareBoxID" : selfCareBoxID,
      "boxDiscountID" : boxDiscountID,
      "customerID" : customerID,
      "quantity" : quantity
	};
	
    return this.httpClient.put<any>(this.baseUrl + "/createSelfCareBoxOrder/",createNewSelfCareBoxOrder,httpOptions).pipe(
      catchError(this.handleError)
    );
  }

  createTransaction(artworkOrderIdList: number[], selfCareBoxIDList: number[], customerID : number): Observable<any>{
	  let createNewTransaction = {
		"artworkOrderList" : artworkOrderIdList,
		"selfCareList" : selfCareBoxIDList,
		"paymentType" : "MASTERCARD",
		"custID" : customerID
	  }; 
	  return this.httpClient.put<any>(this.baseUrl + "/createTransaction/",createNewTransaction,httpOptions).pipe(
		catchError(this.handleError)
	  );
  }

  private handleError(error: HttpErrorResponse)
	{
		let errorMessage: string = "";
		
		if (error.error instanceof ErrorEvent) 
		{		
			errorMessage = "An unknown error has occurred: " + error.error.message;
		} 
		else 
		{		
			errorMessage = "A HTTP error has occurred: " + `HTTP ${error.status}: ${error.error.message}`;
		}
		
		console.error(errorMessage);
		
		return throwError(errorMessage);		
	}
}
