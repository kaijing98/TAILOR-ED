import { Tag } from './tag';
import { User } from './user';

export class Post {
    postId: number;
    topic: string;
    content: string;
    image: string;
    dateTime: Date;
    tags: Tag[];
    user: User;

    constructor(topic?:string, postId?:number, content?:string, image?:string, dateTime?:Date) {
        this.topic = topic,
        this.postId = postId,
        this.content = content,
        this.image = image,
        this.dateTime = new Date();
    }
}
