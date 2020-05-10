export class ArtworkPrice {
    artworkPriceId : number;
    formatEnum : string;
    formatEnumStr : string;
    price : number;

    constructor(artworkPriceId?: number, formatEnum?:string,formatEnumStr?:string,price?:number){
        this.artworkPriceId = artworkPriceId;
        this.formatEnum = formatEnum;
        this.formatEnumStr = formatEnumStr;
        this.price = price;
    }
}
