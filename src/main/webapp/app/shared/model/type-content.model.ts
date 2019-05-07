import { Moment } from 'moment';
import { IContent } from 'app/shared/model/content.model';

export interface ITypeContent {
    id?: number;
    nameTypeContent?: string;
    priority?: number;
    createdDate?: Moment;
    createdBy?: number;
    updatedDate?: Moment;
    updatedBy?: number;
    typeContentIDS?: IContent[];
}

export class TypeContent implements ITypeContent {
    constructor(
        public id?: number,
        public nameTypeContent?: string,
        public priority?: number,
        public createdDate?: Moment,
        public createdBy?: number,
        public updatedDate?: Moment,
        public updatedBy?: number,
        public typeContentIDS?: IContent[]
    ) {}
}
