import { SelfCareBox } from './selfCareBox';
import { Seller } from './seller';

export class SelfCareBoxOrder {
    
    orderId: number;
    quantity: number;
    pricePerMthAtPurchase: number;
    totalPriceAtPurchase: number;
    selfCareBox: SelfCareBox;
    seller : Seller;

    constructor(orderId?: number, quantity?: number, pricePerMthAtPurchase?:number, totalPriceAtPurchase?:number){
        this.orderId = orderId;
        this.quantity = quantity;
        this.pricePerMthAtPurchase = pricePerMthAtPurchase;
        this.totalPriceAtPurchase = totalPriceAtPurchase;
    }
}
