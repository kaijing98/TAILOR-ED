export class ShoppingCartItem {
    productName : string;
    productPrice : number;
    productPriceID : number;
    productQuantity: number;
    productType: string;
    productDescription: string;
    productImage: string;
    productId : number;
    constructor(productId?: number,productName?: string, productPrice?: number,productPriceID?: number, productQuantity?: number, productType?: string, productImage?: string){
        this.productId = productId;
        this.productName = productName;
        this.productPrice = productPrice;
        this.productPriceID = productPriceID;
        this.productQuantity = productQuantity;
        this.productType = productType;
        this.productImage = productImage;
    }
    
}
