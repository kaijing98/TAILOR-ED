import { Seller } from './seller';

export class SelfCareBox {
    selfCareBoxId: number;
    name: string;
    description: string;
    image: string;
    pricePerMonth: number;
    seller: Seller;

    constructor(selfCareBoxId?: number, name?: string, description?:string, image?:string, pricePerMonth?:number){
        this.selfCareBoxId = selfCareBoxId;
        this.name = name;
        this.description = description;
        this.image = image;
        this.pricePerMonth = pricePerMonth;
    }
}
