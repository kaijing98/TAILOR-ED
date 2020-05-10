export class SelfCareSubscriptionDiscount {
    discountId : number;
    durationEnum : string;
    durationEnumStr : string;
    discountPercentage : number;

    constructor(discountId?: number, durationEnum?:string, durationEnumStr?:string, discount?:number){
        this.discountId = discountId;
        this.durationEnum = durationEnum;
        this.durationEnumStr = durationEnumStr;
        this.discountPercentage = discount;
    }
}