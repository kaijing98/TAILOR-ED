import { Component, OnInit } from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {Router} from '@angular/router';

import {SessionServiceService} from '../../services/session-service.service';
import {ArtworkServiceService} from '../../services/artwork-service.service';
import {Artwork} from '../../class/artwork';
import {ArtworkPrice} from '../../class/artwork-price';
import { ShoppingCartItem } from '../../class/shopping-cart-item';

import {NgbModal} from '@ng-bootstrap/ng-bootstrap';

import {ModalDismissReasons} from '@ng-bootstrap/ng-bootstrap';
import { CustomerServiceService } from 'app/services/customer-service.service';
import { Offences } from 'app/class/offences';
import { Seller } from 'app/class/seller';

@Component({
  selector: 'app-view-artwork-detail',
  templateUrl: './view-artwork-detail.component.html',
  styleUrls: ['./view-artwork-detail.component.css']
})
export class ViewArtworkDetailComponent implements OnInit {

  artworkId: number;
  artworkToView: Artwork;
  artworkPrices: ArtworkPrice[];
  artworkPriceToShow : ArtworkPrice;
  sellerIsVerified: boolean;
  selectedPrice : number;
  reportSuccess: boolean;
  userIsBanned: boolean;

  selectedQuantity: number;

  selectedPriceID : number;

  lblPriceContent : number;

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
    private artworkService: ArtworkServiceService,
    private customerService: CustomerServiceService,
    private modalService: NgbModal
  ) { 

  }

  ngOnInit(): void {
    this.artworkId = parseInt(this.activatedRoute.snapshot.paramMap.get('artworkID'));
    
    this.selectedQuantity = 1;

    this.artworkService.getViewArtwork(this.artworkId).subscribe(
      response => {
        let artwork : Artwork = response.artwork;

        this.artworkToView = artwork;
        this.sellerIsVerified = artwork.seller.isVerified;
        this.artworkPrices = response.artwork.artworkPrices;
        this.selectedPrice = this.artworkPrices[0].price;
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


  setPriceOnPage(event: Event){
    let selectElementText = event.target['options'].selectedIndex.text;
    this.lblPriceContent = selectElementText;
  }

  public addToCart(){
    let shoppingCart : ShoppingCartItem[] = this.sessionService.getShoppingCart();

    this.artworkPrices.forEach(element =>{
      if(element.price == this.selectedPrice)
      this.selectedPriceID = element.artworkPriceId;
      console.log("selected artworkPrice id+" + this.selectedPriceID);
    
    });

    let exist : boolean;
    exist = false;
    
    shoppingCart.forEach(
      element => {
        if(element.productId == this.artworkToView.artworkId && 
          element.productPriceID == this.selectedPriceID){
            element.productQuantity += this.selectedQuantity;
            this.sessionService.setShoppingCart(shoppingCart);
            exist = true;
        }
      }
    );

    if(exist == false){
      let shoppingCartItem : ShoppingCartItem;
      shoppingCartItem = new ShoppingCartItem(this.artworkToView.artworkId,this.artworkToView.name,this.selectedPrice,this.selectedPriceID,this.selectedQuantity,"Artwork",this.artworkToView.image);
      this.sessionService.addShoppingCartItem(shoppingCartItem);
    }



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
