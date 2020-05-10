import { Component, OnInit } from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {Router} from '@angular/router';

import {SessionServiceService} from '../../services/session-service.service';
import {ArtworkServiceService} from '../../services/artwork-service.service';
import {Artwork} from '../../class/artwork';
import {ArtworkPrice} from '../../class/artwork-price';
import {SelfCareBoxServiceService} from '../../services/selfCareBox-service.service';
import {SelfCareBox} from '../../class/selfCareBox';
import {SelfCareSubscriptionDiscount} from '../../class/selfCareBox-discount';
import { ShoppingCartItem } from '../../class/shopping-cart-item';
import {Customer} from '../../class/customer';

import {CustomerServiceService} from '../../services/customer-service.service';

import {CheckoutService} from '../../services/checkout.service';

import {NgbModal, ModalDismissReasons} from '@ng-bootstrap/ng-bootstrap';

interface Alert {
  type: string;
  message: string;
}

@Component({
  selector: 'app-shopping-cart',
  templateUrl: './shopping-cart.component.html',
  styleUrls: ['./shopping-cart.component.css']
})

export class ShoppingCartComponent implements OnInit {
  alerts: Alert[];
  artworkId: number;
  artworkToView: Artwork;
  artworkPrices: ArtworkPrice[];
  artworkPriceToShow : ArtworkPrice;

  selfCareBoxId: number;
  selfCareBoxToView: SelfCareBox;
  boxDiscounts: SelfCareSubscriptionDiscount[];
  boxDiscountToShow : SelfCareSubscriptionDiscount;

  selectedPrice : number;
  selectedDiscount : number;
  
  lblPriceContent : number;
  lblDiscountContent : number;

  closeResult = '';

  shoppingCart : ShoppingCartItem[];

  subtotalAmt: number;
  totalAmt : number;
  ShippingAndHandling: number;
  tax : number;


  resultSuccess : boolean;
  resultError : boolean;
  message : string;

  constructor(
    private router: Router,
    private activatedRoute: ActivatedRoute,
    public sessionService: SessionServiceService,
    private artworkService: ArtworkServiceService,
    private selfCareBoxService: SelfCareBoxServiceService,
    private customerService: CustomerServiceService,
    private modalService : NgbModal,
    private checkoutService : CheckoutService
  ) { 
    this.resultSuccess = false;
    this.resultError = false;
  }

  close(alert: Alert) {
    this.alerts.splice(this.alerts.indexOf(alert), 1);
  }

  ngOnInit(): void {

    this.shoppingCart = this.sessionService.getShoppingCart();

    this.ShippingAndHandling = 0;
    this.tax = 0;
    this.totalAmt = 0;
    this.subtotalAmt = 0;

    this.shoppingCart.forEach(element => {
      let curPrice : number = Number(element.productPrice);
      curPrice = curPrice * element.productQuantity;
      this.subtotalAmt += curPrice;
      this.ShippingAndHandling += (curPrice * 0.05);
      console.log("Current Ship = " + this.ShippingAndHandling);
      this.tax += (curPrice * 0.07);
    });

    this.totalAmt = this.subtotalAmt + this.ShippingAndHandling + this.tax;

    console.log("Checking shopping cart: " + this.shoppingCart.toString());

  }

  quantityOnChange(i : number ){
    console.log(i);
    let quantityVar : string = (<HTMLInputElement>document.getElementById(i.toString())).value;
    console.log("QuantityValue : " + quantityVar);

    this.shoppingCart[i].productQuantity = +quantityVar;
    console.log("ShoppingCart.Quantity: " + this.shoppingCart[i].productQuantity);

    this.ShippingAndHandling = 0;
    this.tax = 0;
    this.totalAmt = 0;
    this.subtotalAmt = 0;
    
    this.shoppingCart.forEach(element => {
      let curPrice : number = Number(element.productPrice);
      curPrice = curPrice * element.productQuantity;
      this.subtotalAmt += curPrice;
      this.ShippingAndHandling += (curPrice * 0.05);
      console.log("Current Ship = " + this.ShippingAndHandling);
      this.tax += (curPrice * 0.07);

    });

    this.totalAmt = this.subtotalAmt + this.ShippingAndHandling + this.tax;

    console.log("Checking shopping cart: " + this.shoppingCart.toString());
  }

  public removeShoppingCartItem (shoppingCartItem : ShoppingCartItem){
    this.shoppingCart.forEach ((item,index) => {
      if(item == shoppingCartItem) this.shoppingCart.splice(index,1);
    }
    );

    this.sessionService.setShoppingCart(this.shoppingCart);

    this.ShippingAndHandling = 0;
    this.tax = 0;
    this.totalAmt = 0;
    this.subtotalAmt = 0;

    this.shoppingCart.forEach(element => {
      let curPrice : number = Number(element.productPrice);
      this.subtotalAmt += curPrice;
      this.ShippingAndHandling += (curPrice * 0.05);
      console.log("Current Ship = " + this.ShippingAndHandling);
      this.tax += (curPrice * 0.07);
    });

    this.totalAmt = this.subtotalAmt + this.ShippingAndHandling + this.tax;
  }

  public checkout (content){
    let loginedCustomer : Customer = this.sessionService.getCustomer();
    
    // Create artwork order list for transaction
    let artworkOrderList : number[] = new Array();

    // Create self care list for transaction
    let selfCareBoxOrderList : number[] = new Array();
    
    let transactionID : number;

    if(loginedCustomer == null){
      console.log("Please sign in");
      this.open(content);
    }
    else {
      let shoppingCart : ShoppingCartItem[] = this.shoppingCart;
      shoppingCart.forEach(element => {
        if(element.productType == "Artwork"){
          console.log("**********************************");
          console.log("Product ID: " + element.productId);
          console.log("Product PriceID: " + element.productPriceID);
          console.log("Logined Customer: " + loginedCustomer.userId);
          console.log("Product Quantity: " + element.productQuantity );
          
          this.checkoutService.createArtworkOrder(element.productId,element.productPriceID,loginedCustomer.userId,element.productQuantity).subscribe(
            response => {
              let newArtworkOrderId : number = response.artworkOrderID;
              this.resultSuccess = true;
              this.resultError = false;
              console.log("New Artwork Order ID: " + newArtworkOrderId);
              artworkOrderList.push(newArtworkOrderId);
            }, 
            error => {
              this.resultError = true;
              this.resultSuccess = false;

              console.log('******* Shopping cart create artwork order success: ' + error); 
            }
          );
        } else {
          console.log("**********************************");
          console.log("Product ID: " + element.productId);
          console.log("Product PriceID: " + element.productPriceID);
          console.log("Logined Customer: " + loginedCustomer.userId);
          console.log("productPriceId: " + element.productPriceID);

          console.log("Product Quantity: " + element.productQuantity );

          this.checkoutService.createSelfCareBoxOrder(element.productId, element.productPriceID, loginedCustomer.userId,element.productQuantity).subscribe(
            response => {
              let newSelfCareBoxOrderId : number = response.selfCareBoxOrderID;
              this.resultSuccess = true;
              this.resultError = false;
              console.log("New SelfCareBox Order ID: " + newSelfCareBoxOrderId);
              selfCareBoxOrderList.push(newSelfCareBoxOrderId);
            }, 
            error => {
              this.resultError = true;
              this.resultSuccess = false;

              console.log('******* Shopping cart create SelfCareBox order success: ' + error); 
            }
          );
        }
      });
      this.checkoutService.createTransaction(artworkOrderList,selfCareBoxOrderList,loginedCustomer.userId).subscribe(
        response => {
          let transactionID : number = response.transactionID;
          this.resultSuccess = true;
          this.resultError = false;
          console.log("New Transaction Order ID: " + transactionID);
          //at this point checkout success, we show an alert to notify user it is successful
          this.alerts = [{
            type: 'success',
            message: 'Orders checked out successfully! Your products are now on the way.'
          }];
          //re-set everything to zero
          this.shoppingCart = [];
          this.sessionService.setShoppingCart(this.shoppingCart);
          this.ShippingAndHandling = 0;
          this.tax = 0;
          this.totalAmt = 0;
          this.subtotalAmt = 0;
        }, 
        error => {
          this.resultError = true;
          this.resultSuccess = false;

          console.log('******* Shopping cart create transaction not success: ' + error); 
        }
      );
    }

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

  public redirectToRegister(){
    this.modalService.dismissAll();
    this.router.navigate(['/pages/registerCustomer']);
  }

  public redirectToViewOrders(){
    this.modalService.dismissAll();
    this.router.navigate(['/pages/viewAllMyOrders']);
  }

}
