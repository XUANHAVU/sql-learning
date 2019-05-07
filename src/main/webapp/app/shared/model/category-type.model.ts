import { Moment } from 'moment';
import { ICategory } from 'app/shared/model/category.model';

export interface ICategoryType {
    id?: number;
    nameCategoryType?: string;
    description?: string;
    createdDate?: Moment;
    createdBy?: number;
    updatedDate?: Moment;
    updatedBy?: number;
    categoryTypeIDS?: ICategory[];
}

export class CategoryType implements ICategoryType {
    constructor(
        public id?: number,
        public nameCategoryType?: string,
        public description?: string,
        public createdDate?: Moment,
        public createdBy?: number,
        public updatedDate?: Moment,
        public updatedBy?: number,
        public categoryTypeIDS?: ICategory[]
    ) {}
}
