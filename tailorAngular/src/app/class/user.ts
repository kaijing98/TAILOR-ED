export class User {
    userId:number;
    username:string;
    firstName:string;
    lastName:string;
    email:string;
    isDeleted:boolean;

    constructor(userId?:number, usename?:string, firstName?:string, lastName?:string, email?:string, isDeleted?:boolean) {
        this.userId = userId,
        this.username = usename,
        this,firstName = firstName,
        this.lastName = lastName,
        this.email = email,
        this.isDeleted = isDeleted
    }
}
