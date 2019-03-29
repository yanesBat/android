export const enum NetworkOperator {
    MOVISTAR = 'MOVISTAR',
    CLARO = 'CLARO',
    CNT = 'CNT',
    TUENTI = 'TUENTI'
}

export interface IPhoneNumber {
    id?: number;
    number?: string;
    networkOperator?: NetworkOperator;
    hasWhatsapp?: boolean;
    eBCardId?: number;
}

export class PhoneNumber implements IPhoneNumber {
    constructor(
        public id?: number,
        public number?: string,
        public networkOperator?: NetworkOperator,
        public hasWhatsapp?: boolean,
        public eBCardId?: number
    ) {
        this.hasWhatsapp = this.hasWhatsapp || false;
    }
}
