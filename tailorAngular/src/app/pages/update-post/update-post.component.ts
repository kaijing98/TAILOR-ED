import { Component, OnInit } from '@angular/core';
import { Post } from 'app/class/post';
import { PostsService } from 'app/services/posts.service';
import { ActivatedRoute, Router } from '@angular/router';
import { SessionServiceService } from 'app/services/session-service.service';
import { NgForm } from '@angular/forms';

@Component({
  selector: 'app-update-post',
  templateUrl: './update-post.component.html',
  styleUrls: ['./update-post.component.css']
})
export class UpdatePostComponent implements OnInit {
  postId: number;
  postToUpDate: Post;
  submitted: boolean;
  retrievePostError: boolean;

  resultSuccess: boolean;
  resultError: boolean;
  message: string;


  constructor(private router: Router,
    private activatedRoute: ActivatedRoute,
    public sessionService: SessionServiceService,
    private postService: PostsService, ) {
    this.submitted = false;
    this.retrievePostError = false;
    this.resultSuccess = false;
    this.resultError = false;

  }

  ngOnInit(): void {

    this.postId = parseInt(this.activatedRoute.snapshot.paramMap.get('postId'));
    console.log('postId: retrieve: ' + this.postId);

    this.postService.getPostById(this.postId).subscribe(
      response => {
        this.postToUpDate = response.post;
      },
      error => {
        this.retrievePostError = true;
        console.log('***************UpdatePostComponent.ts: ' + error);
      }
    );
  }

  update(updatePostForm: NgForm) {
    this.submitted = true;
    if (updatePostForm.valid) {
      this.postService.updatePost(this.postToUpDate).subscribe(
        response => {
          this.resultSuccess = true;
          this.resultError = false;
          this.message = "Post updated successfully";
        }, 
        error => {
          this.resultError = true;
          this.resultSuccess = false;
          this.message = "An error has occurred while updating the post: " + error;
          console.log('********** UpdatePostComponent.ts: ' + error);
        } 
      )
    }
  }
}

