import { ArtworkOrder } from './../../class/artwork-order';
import { Component, OnInit } from '@angular/core';
import { ArtworkServiceService } from 'app/services/artwork-service.service';
import { CustomerServiceService } from 'app/services/customer-service.service';
import { SessionServiceService } from 'app/services/session-service.service';
import { Router, ActivatedRoute } from '@angular/router';
import { SelfCareBoxOrder } from 'app/class/self-care-box-order';

@Component({
  selector: 'app-view-all-my-orders',
  templateUrl: './view-all-my-orders.component.html',
  styleUrls: ['./view-all-my-orders.component.css']
})
export class ViewAllMyOrdersComponent implements OnInit {
  username: string;
  password: string;
  userId: number;
  artworkOrders: ArtworkOrder[];
  selfCareBoxOrders: SelfCareBoxOrder[];


  constructor(private artworkService: ArtworkServiceService,
    private customerService: CustomerServiceService,
    private sessionService: SessionServiceService,
    private router: Router,
    private activatedRoute: ActivatedRoute) {
      
     }

  ngOnInit(): void {
    this.username = this.sessionService.getCustomer().username;
    this.password = this.sessionService.getCustomer().password;
    this.userId = this.sessionService.getCustomer().userId;
    console.log(this.userId);
    this.getAllMyArtworkOrder(this.userId);
    this.getAllMySelfCareOrder(this.userId);
  }


  getAllMyArtworkOrder(customerId) {
    this.customerService.getAllMyArtworkOrders(customerId).subscribe(
      response => {
        this.artworkOrders = response.artworkOrders;
      },
      error => {
        console.log('********** ViewAllMyOrdersComponent.ts: ' + error);
      }
    )
  }

  getAllMySelfCareOrder(customerId) {
    this.customerService.getAllMySelfCareBoxOrders(customerId).subscribe(
      response => {
        this.selfCareBoxOrders = response.selfCareBoxOrders;
        console.log(this.selfCareBoxOrders.length);
      },
      error => {
        console.log('********** ViewAllMyOrdersComponent.ts: ' + error);
      }
    )
  }
}
