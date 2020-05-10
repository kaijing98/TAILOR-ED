import { Injectable } from '@angular/core';
import { SessionServiceService } from './session-service.service';

import { HttpClient, HttpHeaders, HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';

import { Customer } from '../class/customer';
import { Tag } from '../class/tag';

const httpOptions = {
    headers: new HttpHeaders({'Content-Type': 'application/json'})
}

@Injectable({
    providedIn: 'root'
})

export class CustomerServiceService {

    baseUrl : string = 'http://localhost:8080/tailoredJsfRws/Resources/Customer';

    customer: Customer;

    constructor(private httpClient: HttpClient) {
    }

    customerLogin(username: string, password: string): Observable<any> {
        return this.httpClient.get<any>(this.baseUrl + "/customerLogin/?username=" + username + "&password=" + password).pipe
        (
            
            catchError(this.handleError)
        );
    }

    createNewCustomer(newCustomer: Customer, selectedTags: Tag[]): Observable<any> {

        let createCustomerReq = {'newCustomer': newCustomer, 'tags': selectedTags};

        return this.httpClient.put<any>(this.baseUrl, createCustomerReq, httpOptions).pipe
        (
            catchError(this.handleError)
        );
    }

    updateCustomer(customer: Customer): Observable<any> {
        let updateCustomerReq = {'customer': customer};

        return this.httpClient.post<any>(this.baseUrl + "/updateProfile", updateCustomerReq, httpOptions).pipe
        (
            catchError(this.handleError)
        );
    }

    updatePreferences(customer: Customer, selectedTags: Tag[]): Observable<any> {
        let updatePreferencesReq = {'customer': customer, 'tags': selectedTags};

        return this.httpClient.post<any>(this.baseUrl + "/updatePreference", updatePreferencesReq, httpOptions).pipe
        (
            catchError(this.handleError)
        );
    }

    deleteCustomer(customerId: number): Observable<any> {
        return this.httpClient.delete<any>(this.baseUrl + "/" + customerId).pipe
        (
            catchError(this.handleError)
        );
    }

    getAllTags(): Observable<any> {
        return this.httpClient.get<any>("http://localhost:8080/tailoredJsfRws/Resources/Browsering/retrieveAllTags").pipe
        (
            catchError(this.handleError)
        );
    }

    getAllOffences(): Observable<any> {
        return this.httpClient.get<any>("http://localhost:8080/tailoredJsfRws/Resources/Customer/retrieveAllOffences").pipe
        (
            catchError(this.handleError)
        );
    }

    getAllMyArtworkOrders(customerId): Observable<any> {
		return this.httpClient.get<any>("http://localhost:8080/tailoredJsfRws/Resources/Checkout/retrieveArtworkOrderById/" + customerId).pipe
			(
				catchError(this.handleError)
			);
    }
    
    getAllMySelfCareBoxOrders(customerId): Observable<any> {
		return this.httpClient.get<any>("http://localhost:8080/tailoredJsfRws/Resources/Checkout/retrieveSelfCareBoxOrderById/" + customerId).pipe
			(
				catchError(this.handleError)
			);
	}

    reportUser(userId: number, offenceId: number): Observable<any> {
        return this.httpClient.post<any>(this.baseUrl + "/reportUser/?userId=" + userId + "&offenceId=" + offenceId, httpOptions).pipe
        (
            
            catchError(this.handleError)
        );
    }

    private handleError(error: HttpErrorResponse)
    {
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
