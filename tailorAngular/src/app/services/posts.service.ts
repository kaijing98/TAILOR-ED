import { catchError } from 'rxjs/operators';
import { Injectable, ErrorHandler } from '@angular/core';
import { HttpHeaders, HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { Post } from 'app/class/post';
import { Tag } from 'app/class/tag';
import { Customer } from 'app/class/customer';

const httpOptions = {
	headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable({
	providedIn: 'root'
})

export class PostsService {
	baseUrl: string = 'http://localhost:8080/tailoredJsfRws/Resources/Post';
	post: Post;

	constructor(private httpClient: HttpClient) {
	}

	getAllPosts(): Observable<any> {
		return this.httpClient.get<any>(this.baseUrl + "/retrieveAllPosts").pipe
			(
				catchError(this.handleError)
			);
	}

	getAllMyPosts(customerId): Observable<any> {
		return this.httpClient.get<any>(this.baseUrl + "/retrieveAllMyPosts/" + customerId).pipe
			(
				catchError(this.handleError)
			);
	}

	getFilteredPost(tagId: number): Observable<any> {
		return this.httpClient.get<any>(this.baseUrl + "/filterPostByTagId/" + tagId).pipe
			(
				catchError(this.handleError)
			);
	}

	updatePost(post: Post): Observable<any> {
		let updatePostReq = { 'post': post };

		return this.httpClient.post<any>(this.baseUrl + "/updatePost", updatePostReq, httpOptions).pipe
			(
				catchError(this.handleError)
			);
	}

	getPostById(postId : number) {
		return this.httpClient.get<any>(this.baseUrl + "/getPostById/" + postId).pipe
			(
				catchError(this.handleError)
			);
	}

	deletePost(postId :number): Observable<any> {
		return this.httpClient.delete<any>(this.baseUrl + "/deletePostById/" + postId).pipe
		(
			catchError(this.handleError)
		);
	}

	createPost(customerId: number, newPost: Post, associatedTags: number[]): Observable<any> {
		let createPostReq = {'customerId': customerId, 'post': newPost, 'tags': associatedTags };
		return this.httpClient.put<any>(this.baseUrl, createPostReq, httpOptions).pipe 
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


	private handleError(error: HttpErrorResponse) {
		let errorMessage: string = "";

		if (error.error instanceof ErrorEvent) {
			errorMessage = "An unknown error has occurred: " + error.error.message;
		}
		else {
			errorMessage = "A HTTP error has occurred: " + `HTTP ${error.status}: ${error.error.message}`;
		}

		console.error(errorMessage);

		return throwError(errorMessage);
	}
}
