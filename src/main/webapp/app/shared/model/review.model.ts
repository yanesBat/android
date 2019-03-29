export interface IReview {
    id?: number;
    rating?: number;
    comment?: string;
    userPropioId?: number;
    eBCardId?: number;
}

export class Review implements IReview {
    constructor(
        public id?: number,
        public rating?: number,
        public comment?: string,
        public userPropioId?: number,
        public eBCardId?: number
    ) {}
}
