import { Moment } from 'moment';
import { IContent } from 'app/shared/model/content.model';
import { IExercises } from 'app/shared/model/exercises.model';

export interface ICategory {
    id?: number;
    nameCategory?: string;
    title?: string;
    isKeyword?: boolean;
    description?: string;
    nameTableData?: string;
    createdDate?: Moment;
    createdBy?: number;
    updatedDate?: Moment;
    updatedBy?: number;
    categoryTypeId?: number;
    cateIDS?: IContent[];
    categoryIDS?: IExercises[];
}

export class Category implements ICategory {
    constructor(
        public id?: number,
        public nameCategory?: string,
        public title?: string,
        public isKeyword?: boolean,
        public description?: string,
        public nameTableData?: string,
        public createdDate?: Moment,
        public createdBy?: number,
        public updatedDate?: Moment,
        public updatedBy?: number,
        public categoryTypeId?: number,
        public cateIDS?: IContent[],
        public categoryIDS?: IExercises[]
    ) {
        this.isKeyword = this.isKeyword || false;
    }
}
