import { IEBCard } from 'app/shared/model/eb-card.model';

export interface IUserPropio {
    id?: number;
    name?: string;
    personals?: IEBCard[];
    wallets?: IEBCard[];
    reviewId?: number;
}

export class UserPropio implements IUserPropio {
    constructor(
        public id?: number,
        public name?: string,
        public personals?: IEBCard[],
        public wallets?: IEBCard[],
        public reviewId?: number
    ) {}
}
