import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IOrders } from 'app/shared/model/orders.model';
import { AccountService } from 'app/core';
import { OrdersService } from './orders.service';

@Component({
    selector: 'jhi-orders',
    templateUrl: './orders.component.html'
})
export class OrdersComponent implements OnInit, OnDestroy {
    orders: IOrders[];
    currentAccount: any;
    eventSubscriber: Subscription;
    currentSearch: string;

    constructor(
        protected ordersService: OrdersService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected activatedRoute: ActivatedRoute,
        protected accountService: AccountService
    ) {
        this.currentSearch =
            this.activatedRoute.snapshot && this.activatedRoute.snapshot.params['search']
                ? this.activatedRoute.snapshot.params['search']
                : '';
    }

    loadAll() {
        if (this.currentSearch) {
            this.ordersService
                .search({
                    query: this.currentSearch
                })
                .pipe(
                    filter((res: HttpResponse<IOrders[]>) => res.ok),
                    map((res: HttpResponse<IOrders[]>) => res.body)
                )
                .subscribe((res: IOrders[]) => (this.orders = res), (res: HttpErrorResponse) => this.onError(res.message));
            return;
        }
        this.ordersService
            .query()
            .pipe(
                filter((res: HttpResponse<IOrders[]>) => res.ok),
                map((res: HttpResponse<IOrders[]>) => res.body)
            )
            .subscribe(
                (res: IOrders[]) => {
                    this.orders = res;
                    this.currentSearch = '';
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    search(query) {
        if (!query) {
            return this.clear();
        }
        this.currentSearch = query;
        this.loadAll();
    }

    clear() {
        this.currentSearch = '';
        this.loadAll();
    }

    ngOnInit() {
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInOrders();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IOrders) {
        return item.id;
    }

    registerChangeInOrders() {
        this.eventSubscriber = this.eventManager.subscribe('ordersListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
