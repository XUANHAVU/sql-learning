import { IOrders } from 'app/shared/model/orders.model';

export interface IShipper {
    id?: number;
    shipperName?: string;
    phone?: string;
    shipperIDS?: IOrders[];
}

export class Shipper implements IShipper {
    constructor(public id?: number, public shipperName?: string, public phone?: string, public shipperIDS?: IOrders[]) {}
}
