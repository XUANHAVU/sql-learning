import { IOrders } from 'app/shared/model/orders.model';

export interface ICustomer {
    id?: number;
    customerName?: string;
    contactName?: string;
    address?: string;
    city?: string;
    postalCode?: string;
    country?: string;
    customerIDS?: IOrders[];
}

export class Customer implements ICustomer {
    constructor(
        public id?: number,
        public customerName?: string,
        public contactName?: string,
        public address?: string,
        public city?: string,
        public postalCode?: string,
        public country?: string,
        public customerIDS?: IOrders[]
    ) {}
}
