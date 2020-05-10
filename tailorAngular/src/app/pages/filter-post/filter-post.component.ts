import { Component, OnInit } from '@angular/core';
import { PostsService } from 'app/services/posts.service';
import { Router, ActivatedRoute } from '@angular/router';
import { Post } from 'app/class/post';
import { CustomerServiceService } from 'app/services/customer-service.service';
import { Tag } from 'app/class/tag';
import { Offences } from 'app/class/offences';
import {ModalDismissReasons, NgbModal} from "@ng-bootstrap/ng-bootstrap";

@Component({
  selector: 'app-filter-post',
  templateUrl: './filter-post.component.html',
  styleUrls: ['./filter-post.component.css']
})
export class FilterPostComponent implements OnInit {
  filteredPosts: Post[];
  tagId: number;
  errorMessage: string;
  offences: Offences[];
  reportSuccess: boolean;
  userIsBanned: boolean;
  closeResult = '';
  
  constructor(private postsService: PostsService,
    private router: Router,
    private customerService: CustomerServiceService,
    private activatedRoute: ActivatedRoute,
              private modalService : NgbModal) { }

  ngOnInit(): void {
    this.tagId = parseInt(this.activatedRoute.snapshot.paramMap.get('tagId'));
    console.log('tagId: retrieve: ' + this.tagId);

    this.postsService.getFilteredPost(this.tagId).subscribe(
      response => {
        this.filteredPosts = response.posts;
      },
      error => {
        console.log('********** ViewFilteredPostComponent.ts: ' + error);
      }
    );
    this.customerService.getAllOffences().subscribe(
      response => {
        this.offences = response.offences
      },
      error => {
        this.errorMessage = error
      }
    );
  }

  reportUser(userId, offenceId, content) {
    this.customerService.reportUser(userId, offenceId).subscribe(
      response => {
        this.reportSuccess = true;
        this.open(content);
      },
      error => {
        if (error.toString().includes("is already Banned")) {
          this.userIsBanned = true;
          this.reportSuccess = false;
        } else {
          this.reportSuccess = false;
        }
        this.open(content);
        console.log('********** StoryboardComponent.ts: ' + error);
      }
    );
  }

  open(content){
    this.modalService.open(content, {ariaLabelledBy: 'modal-basic-title'}).result.then((result) => {
      this.closeResult = `Closed with: ${result}`;
    }, (reason) => {
      this.closeResult = `Dismissed ${this.getDismissReason(reason)}`;
    });
  }

  private getDismissReason(reason: any): string {
    if (reason === ModalDismissReasons.ESC) {
      return 'by pressing ESC';
    } else if (reason === ModalDismissReasons.BACKDROP_CLICK) {
      return 'by clicking on a backdrop';
    } else {
      return `with: ${reason}`;
    }
  }
}
