export class Customer {
    userId: number;
    username: string;
    password: string;
    firstName: string;
    lastName: string;
    email: string;
    isDeleted: boolean;
    isBanned: boolean;
    shippingAddress: string;
    shippingPostalCode: string;
    shippingUnitNum: string;

    constructor(userId?: number, username?: string, password?:string,
                firstName?:string, lastName?:string, email?:string, isDeleted?:boolean,
                isBanned?:boolean, shippingAddress?:string, shippingPostalCode?:string, shippingUnitNum?:string){
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.isDeleted = isDeleted;
        this.isBanned = isBanned;
        this.shippingAddress = shippingAddress;
        this.shippingPostalCode = shippingPostalCode;
        this.shippingUnitNum = shippingUnitNum;
    }
}
