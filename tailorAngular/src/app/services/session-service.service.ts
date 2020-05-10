import { Injectable } from '@angular/core';
import { Artwork } from '../class/artwork';
import { SelfCareBox } from '../class/selfCareBox';
import { Customer } from '../class/customer';
import {ShoppingCartItem } from '../class/shopping-cart-item';

@Injectable({
  providedIn: 'root'
})
export class SessionServiceService {

  constructor() { }

  getShoppingCart() : ShoppingCartItem[]{
    try{
      console.log('*********************************')
      if(sessionStorage.getItem("shoppingCartItems") == null)
      {
        let cart : ShoppingCartItem[] = new Array();
        sessionStorage.setItem("shoppingCartItems", JSON.stringify(cart));
        return cart;
      }
      else
      {
        return JSON.parse(sessionStorage.getItem("shoppingCartItems"));
      }
    }catch{
      let shoppingCart : ShoppingCartItem[];
      return shoppingCart;
    }
  }

  addShoppingCartItem(shoppingCartItem : ShoppingCartItem) : void{
    let cart : ShoppingCartItem[] = this.getShoppingCart();
    cart.push(shoppingCartItem);
    sessionStorage.setItem("shoppingCartItems",JSON.stringify(cart));
  }

  setShoppingCart(shoppingCart : ShoppingCartItem[]){
    try{
      sessionStorage.setItem("shoppingCartItems",JSON.stringify(shoppingCart));
    } catch{

    }
  }

  getViewArtwork() : Artwork{
    try{
      return JSON.parse(sessionStorage.viewArtwork);
    }catch{
      return null;
    }
  }

  setViewArtwork(viewArtwork : Artwork) : void{
    sessionStorage.viewArtwork = JSON.stringify(viewArtwork);
  }


  getArtworks() : Artwork[]{
    try{
      return JSON.parse(sessionStorage.artworks);
    }catch{
      return null;
    }
  }

  setArtworks(artworks : Artwork[]) : void {
    sessionStorage.artworks = JSON.stringify(artworks);
  }

  getViewSelfCareBox() : SelfCareBox{
    try{
      return JSON.parse(sessionStorage.viewSelfCareBox);
    }catch{
      return null;
    }
  }

  setViewSelfCareBox(viewSelfCareBox : SelfCareBox) : void{
    sessionStorage.viewSelfCareBox = JSON.stringify(viewSelfCareBox);
  }


  getSelfCareBoxes() : SelfCareBox[]{
    try{
      return JSON.parse(sessionStorage.selfCareBoxes);
    }catch{
      return null;
    }
  }

  setSelfCareBoxes(selfCareBoxes : SelfCareBox[]) : void {
    sessionStorage.selfCareBoxes = JSON.stringify(selfCareBoxes);
  }

  getCustomer(): Customer {
    try{
      return JSON.parse(sessionStorage.customer);
    }catch{
      return null;
    }
  }

  setCustomer(customer: Customer) : void {
    sessionStorage.customer = JSON.stringify(customer);
  }
}
