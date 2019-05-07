import { Moment } from 'moment';
import { IOrders } from 'app/shared/model/orders.model';

export interface IEmployees {
    id?: number;
    lastName?: string;
    firstName?: string;
    birthDate?: Moment;
    notes?: string;
    employeesIDS?: IOrders[];
}

export class Employees implements IEmployees {
    constructor(
        public id?: number,
        public lastName?: string,
        public firstName?: string,
        public birthDate?: Moment,
        public notes?: string,
        public employeesIDS?: IOrders[]
    ) {}
}
