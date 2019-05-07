import { Moment } from 'moment';

export interface ISQLQuery {
    id?: number;
    title?: string;
    nameUrl?: string;
    query?: string;
    description?: string;
    createdDate?: Moment;
    createdBy?: number;
    updatedDate?: Moment;
    updatedBy?: number;
}

export class SQLQuery implements ISQLQuery {
    constructor(
        public id?: number,
        public title?: string,
        public nameUrl?: string,
        public query?: string,
        public description?: string,
        public createdDate?: Moment,
        public createdBy?: number,
        public updatedDate?: Moment,
        public updatedBy?: number
    ) {}
}
