import { PostsService } from './../../services/posts.service';
import { Component, OnInit } from '@angular/core';
import { Customer } from 'app/class/customer';
import { Router } from '@angular/router';
import { SessionServiceService } from 'app/services/session-service.service';
import { Post } from 'app/class/post';

@Component({
  selector: 'app-view-all-my-posts',
  templateUrl: './view-all-my-posts.component.html',
  styleUrls: ['./view-all-my-posts.component.css']
})
export class ViewAllMyPostsComponent implements OnInit {
  userId: number;
  password: string;
  username:string;
  currentCustomer: Customer;
  myPosts: Post[];

  constructor(
    private router: Router,
    private sessionService: SessionServiceService,
    private postsService: PostsService) {
    this.currentCustomer = new Customer();
  }

  ngOnInit(): void {
    this.username = this.sessionService.getCustomer().username;
    this.password = this.sessionService.getCustomer().password;
    this.userId = this.sessionService.getCustomer().userId;
    console.log("activated");
    console.log(this.userId);
    this.getAllMyPosts(this.userId);
  }

  getAllMyPosts(customerId) {
    this.postsService.getAllMyPosts(customerId).subscribe(
      response => {
        this.myPosts = response.posts;
      },
      error => {
        console.log('********** ViewAllMyPostsComponent.ts: ' + error);
      }
    )
  }
}
