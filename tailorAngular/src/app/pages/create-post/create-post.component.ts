import { Component, OnInit } from '@angular/core';
import { Tag } from 'app/class/tag';
import { Post } from 'app/class/post';
import { Router, ActivatedRoute } from '@angular/router';
import { SessionServiceService } from 'app/services/session-service.service';
import { PostsService } from 'app/services/posts.service';
import { NgForm } from '@angular/forms';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-create-post',
  templateUrl: './create-post.component.html',
  styleUrls: ['./create-post.component.css']
})
export class CreatePostComponent implements OnInit {
  submitted: boolean;
  newPost: Post;
  customerId: number;
  associatedTags: Tag[];
  tags: Tag[];
  resultSuccess: boolean;
  resultError: boolean;
  message: string;
  errorMessage: string;
  tagIds: string[];



  constructor(private router: Router,
    private activatedRoute: ActivatedRoute,
    public sessionService: SessionServiceService,
    private postService: PostsService,
    private modalService: NgbModal) {
    this.submitted = false;
    this.newPost = new Post();
    this.resultSuccess = false;
    this.resultError = false;
  }

  ngOnInit(): void {
    this.customerId = this.sessionService.getCustomer().userId;
    this.postService.getAllTags().subscribe(
      response => {
        this.tags = response.tags
      },
      error => {
        this.errorMessage = error
      }
    );
  }

  clear()
	{
		this.submitted = false;
		this.newPost = new Post();
  }
  
  viewAllMyPost() {
    this.router.navigate(["pages/viewAllMyPosts"]);
}


  create(createPostForm: NgForm) {
    this.submitted = true;

    let longTagIds: number[] = new Array();
		
		for(var i = 0; i < this.tagIds.length; i++)
		{
			longTagIds.push(parseInt(this.tagIds[i]));
		}			
    
		this.submitted = true;

    if (createPostForm.valid) {
      this.postService.createPost(this.customerId, this.newPost, longTagIds).subscribe(
          response => {
            let newPostId: number = response.postId;
            this.resultSuccess = true;
            this.resultError = false;
            this.message = "New post created successfully";
          },
          error => {
            this.resultError = true;
					this.resultSuccess = false;
          this.message = "An error has occurred while creating the new post: " + error;
          console.log('********** CreateNewPostComponent.ts: ' + error);
          }
      )
    }
  }
}
