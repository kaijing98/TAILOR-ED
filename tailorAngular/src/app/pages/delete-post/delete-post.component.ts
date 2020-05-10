import { Component, OnInit } from '@angular/core';
import { Post } from 'app/class/post';
import { Router, ActivatedRoute } from '@angular/router';
import { SessionServiceService } from 'app/services/session-service.service';
import { PostsService } from 'app/services/posts.service';

@Component({
  selector: 'app-delete-post',
  templateUrl: './delete-post.component.html',
  styleUrls: ['./delete-post.component.css']
})
export class DeletePostComponent implements OnInit {
  postId: number;
	postToDelete: Post;
	error: boolean;
	errorMessage: string;


  constructor(private router: Router,
    private activatedRoute: ActivatedRoute,
    public sessionService: SessionServiceService,
    private postService: PostsService) 
    { 
      this.error = false;
    }

  ngOnInit(): void {
    this.postId = parseInt(this.activatedRoute.snapshot.paramMap.get('postId'));
    this.postService.getPostById(this.postId).subscribe(
      response => {
        this.postToDelete = response.post;
      },
      error => {
        this.error = true;
        this.errorMessage = error;
        console.log('***************DeletePostComponent.ts: ' + error);
      }
    );
  }

  deletePost() {
    this.postService.deletePost(this.postId).subscribe(
      response => {
        this.router.navigate(["/pages/viewAllMyPosts"]);
      }, 
      error => {
        this.error = true;
        this.errorMessage = error;
      }
    );
  }
}
