import { Moment } from 'moment';

export interface IOrders {
    id?: number;
    orderDate?: Moment;
    customerId?: number;
    employeesId?: number;
    shipperId?: number;
}

export class Orders implements IOrders {
    constructor(
        public id?: number,
        public orderDate?: Moment,
        public customerId?: number,
        public employeesId?: number,
        public shipperId?: number
    ) {}
}
