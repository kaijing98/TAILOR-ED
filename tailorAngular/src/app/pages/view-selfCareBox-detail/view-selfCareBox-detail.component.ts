import { Component, OnInit } from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {Router} from '@angular/router';

import {SessionServiceService} from '../../services/session-service.service';
import {SelfCareBoxServiceService} from '../../services/selfCareBox-service.service';
import {SelfCareBox} from '../../class/selfCareBox';
import {SelfCareSubscriptionDiscount} from '../../class/selfCareBox-discount';
import { ShoppingCartItem } from '../../class/shopping-cart-item';

import {NgbModal} from '@ng-bootstrap/ng-bootstrap';

import {ModalDismissReasons} from '@ng-bootstrap/ng-bootstrap';
import { CustomerServiceService } from 'app/services/customer-service.service';
import { Offences } from 'app/class/offences';
import { Seller } from 'app/class/seller';

@Component({
  selector: 'app-view-selfCareBox-detail',
  templateUrl: './view-selfCareBox-detail.component.html',
  styleUrls: ['./view-selfCareBox-detail.component.css']
})
export class ViewSelfCareBoxDetailComponent implements OnInit {


  selfCareBoxId: number;
  selfCareBoxToView: SelfCareBox;
  boxDiscounts: SelfCareSubscriptionDiscount[];
  boxDiscountToShow : SelfCareSubscriptionDiscount;
  selectedDiscount : number;
  selectedDiscountID : number;
  lblDiscountContent : number;
  sellerIsVerified: boolean;
  currentDiscount: SelfCareSubscriptionDiscount;
  nunMonths: number;

  selectedQuantity: number;

  purchasePrice: number;
  reportSuccess: boolean;
  userIsBanned: boolean;
  closeResult = '';

  shoppingCart : ShoppingCartItem[];

  subtotalAmt: number;
  totalAmt : number;
  ShippingAndHandling: number;
  tax : number;

  offences: Offences[];
  errorMessage: string;

  constructor(
    private router: Router,
    private activatedRoute: ActivatedRoute,
    public sessionService: SessionServiceService,
    private selfCareBoxService: SelfCareBoxServiceService,
    private modalService: NgbModal,
    private customerService: CustomerServiceService
  ) { 

  }

  ngOnInit(): void {
    this.selfCareBoxId = parseInt(this.activatedRoute.snapshot.paramMap.get('selfCareBoxID'));

    this.selectedQuantity = 1;

    this.selfCareBoxService.getViewSelfCareBox(this.selfCareBoxId).subscribe(
      response => {
        let selfCareBox : SelfCareBox = response.selfCareBox;
        this.selfCareBoxToView = selfCareBox;
        this.sellerIsVerified = selfCareBox.seller.isVerified;
        this.boxDiscounts = response.selfCareBox.selfCareSubscriptionDiscounts;
        console.log("box discounts: " + this.boxDiscounts.length);
        console.log("box discounts: " + this.boxDiscounts[0].discountId);
        this.selectedDiscount = this.boxDiscounts[0].discountPercentage;   
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

  setDiscountOnPage(event: Event){
    let selectElementText = event.target['options'].selectedIndex.text;
    this.lblDiscountContent = selectElementText;
    
  }

  public addToCart(){

    this.boxDiscounts.forEach(element => {
      if(element.discountPercentage == this.selectedDiscount) {
      this.currentDiscount = element;
      this.selectedDiscountID = element.discountId;
      }
    });

    console.log("selected discount id+: " + this.selectedDiscountID);
    if (this.currentDiscount.durationEnumStr == "OnceOff") {
      this.nunMonths = 1;
    } else if (this.currentDiscount.durationEnumStr == "ThreeMonths") {
      this.nunMonths = 3;
    } else { 
      this.nunMonths = 6;
    }
    this.purchasePrice = this.selfCareBoxToView.pricePerMonth*(1 - this.selectedDiscount/100) * this.nunMonths;
    let shoppingCartItem : ShoppingCartItem;
    shoppingCartItem = new ShoppingCartItem(this.selfCareBoxToView.selfCareBoxId,this.selfCareBoxToView.name,this.purchasePrice,this.selectedDiscountID,this.selectedQuantity,"SelfCareBox",this.selfCareBoxToView.image);
    console.log("selected discount Id: " + this.selectedDiscountID); 
    this.sessionService.addShoppingCartItem(shoppingCartItem);

    this.router.navigate(["/pages/shoppingCart"]);
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
