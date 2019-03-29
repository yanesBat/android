import { ICategory } from 'app/shared/model/category.model';
import { IEBCard } from 'app/shared/model/eb-card.model';

export interface ICategory {
    id?: number;
    name?: string;
    description?: string;
    categoryId?: number;
    subCategories?: ICategory[];
    eBCards?: IEBCard[];
}

export class Category implements ICategory {
    constructor(
        public id?: number,
        public name?: string,
        public description?: string,
        public categoryId?: number,
        public subCategories?: ICategory[],
        public eBCards?: IEBCard[]
    ) {}
}
