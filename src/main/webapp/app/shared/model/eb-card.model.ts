import { IReview } from 'app/shared/model/review.model';
import { ISocialContact } from 'app/shared/model/social-contact.model';
import { IPhoneNumber } from 'app/shared/model/phone-number.model';
import { IAddress } from 'app/shared/model/address.model';
import { IUserPropio } from 'app/shared/model/user-propio.model';

export const enum PaidPlan {
    BRONCE = 'BRONCE',
    SILVER = 'SILVER',
    GOLD = 'GOLD'
}

export interface IEBCard {
    id?: number;
    firstName?: string;
    lastName?: string;
    title?: string;
    subtitle?: string;
    bussinesName?: string;
    occupation?: string;
    email?: string;
    rating?: number;
    paidPlan?: PaidPlan;
    imageUrl?: string;
    reviews?: IReview[];
    socialContacts?: ISocialContact[];
    phoneNumbers?: IPhoneNumber[];
    addresses?: IAddress[];
    categoryId?: number;
    users?: IUserPropio[];
    userPropioId?: number;
}

export class EBCard implements IEBCard {
    constructor(
        public id?: number,
        public firstName?: string,
        public lastName?: string,
        public title?: string,
        public subtitle?: string,
        public bussinesName?: string,
        public occupation?: string,
        public email?: string,
        public rating?: number,
        public paidPlan?: PaidPlan,
        public imageUrl?: string,
        public reviews?: IReview[],
        public socialContacts?: ISocialContact[],
        public phoneNumbers?: IPhoneNumber[],
        public addresses?: IAddress[],
        public categoryId?: number,
        public users?: IUserPropio[],
        public userPropioId?: number
    ) {}
}
