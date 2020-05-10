import { Artwork } from './artwork';

export class ArtworkOrder {
    orderId: number;
    quantity: number;
    priceAtTimeOfPurchase: number;
    orderStatusEnum: string;
    artwork: Artwork;

    constructor(orderId?: number, quantity?: number, priceAtTimeOfPurchase?:number, orderStatusEnum?:string){
        this.orderId = orderId;
        this.quantity = quantity;
        this.priceAtTimeOfPurchase = priceAtTimeOfPurchase;
        this.orderStatusEnum = orderStatusEnum;
    }
}
