import { Component, OnInit } from '@angular/core';
import { SelfCareBoxServiceService } from 'app/services/selfCareBox-service.service';
import { Router, ActivatedRoute } from '@angular/router';
import { CustomerServiceService } from 'app/services/customer-service.service';
import { Offences } from 'app/class/offences';
import { SelfCareBox } from 'app/class/selfCareBox';
import { SessionServiceService } from 'app/services/session-service.service';

@Component({
  selector: 'app-filter-selfCareBox',
  templateUrl: './filter-selfCareBox.component.html',
  styleUrls: ['./filter-selfCareBox.component.css']
})

export class FilterSelfCareBoxComponent implements OnInit {
  tagId: number;
  offences: Offences[];
  errorMessage: string;
  filteredSelfCareBox: SelfCareBox[];

  constructor(private selfCareBoxService: SelfCareBoxServiceService,
              private router: Router,
              private customerService: CustomerServiceService,
              private activatedRoute: ActivatedRoute,
              private sessionService: SessionServiceService) {     
     }

  ngOnInit(): void {
    this.tagId = parseInt(this.activatedRoute.snapshot.paramMap.get('tagId'));
    console.log('tagId: retrieve: ' + this.tagId);

    this.customerService.getAllOffences().subscribe(
      response => {
        this.offences = response.offences
      },
      error => {
        this.errorMessage = error
      }
    );

    this.selfCareBoxService.getFilteredSelfCareBox(this.tagId).subscribe(
      response => {
        this.filteredSelfCareBox = response.selfCareBoxes;
        this.sessionService.setSelfCareBoxes(response.selfCareBoxes);
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
