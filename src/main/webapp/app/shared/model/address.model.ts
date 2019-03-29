export interface IAddress {
    id?: number;
    name?: string;
    address?: string;
    latitude?: string;
    longitud?: string;
    eBCardId?: number;
}

export class Address implements IAddress {
    constructor(
        public id?: number,
        public name?: string,
        public address?: string,
        public latitude?: string,
        public longitud?: string,
        public eBCardId?: number
    ) {}
}
