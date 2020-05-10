export class Seller {
    userId:number;
    username:string;
    firstName:string;
    lastName:string;
    email:string;
    isDeleted:boolean;
    isBanned:boolean;
    isVerified:boolean;

    constructor(userId?:number, usename?:string, firstName?:string, lastName?:string, email?:string, isDeleted?:boolean, isBanned?:boolean, isVerified?:boolean) {
        this.userId = userId,
        this.username = usename,
        this,firstName = firstName,
        this.lastName = lastName,
        this.email = email,
        this.isDeleted = isDeleted,
        this.isBanned = isBanned,
        this.isVerified = isVerified
    }
}
