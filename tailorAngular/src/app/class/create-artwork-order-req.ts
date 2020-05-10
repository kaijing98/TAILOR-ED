export class CreateArtworkOrderReq {
    artworkID : number;
    artworkPriceID : number;
    customerID : number;
    quantity : number;

    constructor(    
        artworkID : number,
        artworkPriceID : number,
        customerID : number,
        quantity : number){
            this.artworkID = artworkID;
            this.artworkPriceID = artworkPriceID;
            this.customerID = customerID;
            this.quantity = quantity;
    }
}
