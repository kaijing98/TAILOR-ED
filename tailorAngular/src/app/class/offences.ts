export class Offences {
    offencesId: number;
    name: string;
    description: string;
    numOfPoints: number;


    constructor(offencesId?: number, name?: string, description?: string,
        numOfPoints?: number) {
        this.offencesId = offencesId;
        this.name = name;
        this.description = description;
        this.numOfPoints = numOfPoints;
    }
}
