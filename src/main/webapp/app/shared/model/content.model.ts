import { Moment } from 'moment';

export interface IContent {
    id?: number;
    title?: string;
    content?: string;
    createdDate?: Moment;
    createdBy?: number;
    updatedDate?: Moment;
    updatedBy?: number;
    categoryId?: number;
    typeContentId?: number;
}

export class Content implements IContent {
    constructor(
        public id?: number,
        public title?: string,
        public content?: string,
        public createdDate?: Moment,
        public createdBy?: number,
        public updatedDate?: Moment,
        public updatedBy?: number,
        public categoryId?: number,
        public typeContentId?: number
    ) {}
}
