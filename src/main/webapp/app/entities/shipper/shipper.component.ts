import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IShipper } from 'app/shared/model/shipper.model';
import { AccountService } from 'app/core';
import { ShipperService } from './shipper.service';

@Component({
    selector: 'jhi-shipper',
    templateUrl: './shipper.component.html'
})
export class ShipperComponent implements OnInit, OnDestroy {
    shippers: IShipper[];
    currentAccount: any;
    eventSubscriber: Subscription;
    currentSearch: string;

    constructor(
        protected shipperService: ShipperService,
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
            this.shipperService
                .search({
                    query: this.currentSearch
                })
                .pipe(
                    filter((res: HttpResponse<IShipper[]>) => res.ok),
                    map((res: HttpResponse<IShipper[]>) => res.body)
                )
                .subscribe((res: IShipper[]) => (this.shippers = res), (res: HttpErrorResponse) => this.onError(res.message));
            return;
        }
        this.shipperService
            .query()
            .pipe(
                filter((res: HttpResponse<IShipper[]>) => res.ok),
                map((res: HttpResponse<IShipper[]>) => res.body)
            )
            .subscribe(
                (res: IShipper[]) => {
                    this.shippers = res;
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
        this.registerChangeInShippers();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IShipper) {
        return item.id;
    }

    registerChangeInShippers() {
        this.eventSubscriber = this.eventManager.subscribe('shipperListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
