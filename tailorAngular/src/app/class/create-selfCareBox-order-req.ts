export class CreateSelfCareBoxOrderReq {
    selfCareBoxID : number;
    boxDiscountID : number;
    customerID : number;
    quantity : number;

    constructor(    
        selfCareBoxID : number,
        boxDiscountID : number,
        customerID : number,
        quantity : number){
            this.selfCareBoxID = selfCareBoxID;
            this.boxDiscountID = boxDiscountID;
            this.customerID = customerID;
            this.quantity = quantity;
    }
}
