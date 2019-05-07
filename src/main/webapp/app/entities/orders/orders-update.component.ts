import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';
import { IOrders } from 'app/shared/model/orders.model';
import { OrdersService } from './orders.service';
import { ICustomer } from 'app/shared/model/customer.model';
import { CustomerService } from 'app/entities/customer';
import { IEmployees } from 'app/shared/model/employees.model';
import { EmployeesService } from 'app/entities/employees';
import { IShipper } from 'app/shared/model/shipper.model';
import { ShipperService } from 'app/entities/shipper';

@Component({
    selector: 'jhi-orders-update',
    templateUrl: './orders-update.component.html'
})
export class OrdersUpdateComponent implements OnInit {
    orders: IOrders;
    isSaving: boolean;

    customers: ICustomer[];

    employees: IEmployees[];

    shippers: IShipper[];
    orderDate: string;

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected ordersService: OrdersService,
        protected customerService: CustomerService,
        protected employeesService: EmployeesService,
        protected shipperService: ShipperService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ orders }) => {
            this.orders = orders;
            this.orderDate = this.orders.orderDate != null ? this.orders.orderDate.format(DATE_TIME_FORMAT) : null;
        });
        this.customerService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<ICustomer[]>) => mayBeOk.ok),
                map((response: HttpResponse<ICustomer[]>) => response.body)
            )
            .subscribe((res: ICustomer[]) => (this.customers = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.employeesService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IEmployees[]>) => mayBeOk.ok),
                map((response: HttpResponse<IEmployees[]>) => response.body)
            )
            .subscribe((res: IEmployees[]) => (this.employees = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.shipperService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IShipper[]>) => mayBeOk.ok),
                map((response: HttpResponse<IShipper[]>) => response.body)
            )
            .subscribe((res: IShipper[]) => (this.shippers = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        this.orders.orderDate = this.orderDate != null ? moment(this.orderDate, DATE_TIME_FORMAT) : null;
        if (this.orders.id !== undefined) {
            this.subscribeToSaveResponse(this.ordersService.update(this.orders));
        } else {
            this.subscribeToSaveResponse(this.ordersService.create(this.orders));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IOrders>>) {
        result.subscribe((res: HttpResponse<IOrders>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackCustomerById(index: number, item: ICustomer) {
        return item.id;
    }

    trackEmployeesById(index: number, item: IEmployees) {
        return item.id;
    }

    trackShipperById(index: number, item: IShipper) {
        return item.id;
    }
}
