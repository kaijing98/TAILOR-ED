import { Component, OnInit } from '@angular/core';
import { SelfCareBoxServiceService } from '../../services/selfCareBox-service.service';
import { SelfCareBox } from '../../class/selfCareBox';
import { Router, ActivatedRoute } from '@angular/router';
import { SessionServiceService } from '../../services/session-service.service';
import { CustomerServiceService } from 'app/services/customer-service.service';
import { Tag } from 'app/class/tag';

@Component({
  selector: 'app-shopSelfCareBoxes',
  templateUrl: './shopSelfCareBoxes.component.html',
  styleUrls: ['./shopSelfCareBoxes.component.css']
})
export class ShopSelfCareBoxComponent implements OnInit {

    selfCareBoxes : SelfCareBox[];
    errorMessage: string;
    tags: Tag[];

    constructor(private selfCareBoxService : SelfCareBoxServiceService,
                private customerService: CustomerServiceService,
                private sessionService: SessionServiceService,
                private router: Router,
                private activatedRoute: ActivatedRoute) {
    }

    ngOnInit() {
      this.selfCareBoxService.getSelfCareBoxes().subscribe(
        response => {
          this.selfCareBoxes = response.selfCareBoxes;
          this.sessionService.setSelfCareBoxes(response.selfCareBoxes);
          console.log(this.selfCareBoxes.toString());
        },
        error => {
          this.errorMessage = error
        }
      );
      this.customerService.getAllTags().subscribe(
        response => {
          this.tags = response.tags
        },
        error => {
          this.errorMessage = error
        }
      );
    }

    viewSelfCareBox(selfCareBoxID: number) {
      this.router.navigate(["pages/viewSelfCareBoxDetails/" + selfCareBoxID]);
  }
}
