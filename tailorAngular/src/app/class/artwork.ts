import { Seller } from './seller';

export class Artwork {
    artworkId: number;
    name: string;
    description: string;
    image: string;
    seller: Seller;

    constructor(artworkId?: number, name?: string, description?:string, image?:string){
        this.artworkId = artworkId;
        this.name = name;
        this.description = description;
        this.image = image;
    }
}
