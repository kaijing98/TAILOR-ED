import { PostsService } from '../../services/posts.service';
import { Component, OnInit } from '@angular/core';
import { Post } from 'app/class/post';
import { SessionServiceService } from '../../services/session-service.service';
import { CustomerServiceService } from 'app/services/customer-service.service';
import { Tag } from 'app/class/tag';
import { Router, ActivatedRoute } from '@angular/router';
import { Offences } from 'app/class/offences';

import {NgbModal, ModalDismissReasons} from '@ng-bootstrap/ng-bootstrap';


@Component({
  selector: 'app-landing',
  templateUrl: './storyboard.component.html',
  styleUrls: ['./storyboard.component.scss']
})


export class StoryboardComponent implements OnInit {
  posts: Post[]
  tags: Tag[];
  username: string;
  errorMessage: string;
  tagId: number;
  offences: Offences[];
  reportSuccess: boolean;
  userIsBanned: boolean;


  resultSuccess : boolean;
  resultError : boolean;
  message : string;
  closeResult = '';

  constructor(private postsService: PostsService,
    private customerService: CustomerServiceService, private sessionService: SessionServiceService,
    private router: Router,
    private activatedRoute: ActivatedRoute,
    private modalService : NgbModal) {
    this.postsService.getAllPosts().subscribe(
      response => {
        this.posts = response.posts;
      },
      error => {
        console.log('********** ViewAllPostComponent.ts: ' + error);
      }
    );

    this.customerService.getAllTags().subscribe(
      response => {
        this.tags = response.tags
        console.log("reached all tags");
      },
      error => {
        this.errorMessage = error
        console.log("never reached all tags");
      }
    );

    this.customerService.getAllOffences().subscribe(
      response => {
        this.offences = response.offences
        console.log("reached all offences");
      },
      error => {
        this.errorMessage = error
        console.log("never reached all offences");
      }
    );

    this.reportSuccess = false;
  }

  ngOnInit() {
    // this.username = this.sessionService.getCustomer().username;
    // this.customerService.getAllTags().subscribe(
    //   response => {
    //     this.tags = response.tags
    //     console.log("reached all tags");
    //   },
    //   error => {
    //     this.errorMessage = error
    //     console.log("never reached all tags");
    //   }
    // );

    // this.customerService.getAllOffences().subscribe(
    //   response => {
    //     this.offences = response.offences
    //     console.log("reached all offences");
    //   },
    //   error => {
    //     this.errorMessage = error
    //     console.log("never reached all offences");
    //   }
    // );
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

