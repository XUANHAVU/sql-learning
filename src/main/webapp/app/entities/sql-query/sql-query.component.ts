import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ISQLQuery } from 'app/shared/model/sql-query.model';
import { AccountService } from 'app/core';
import { SQLQueryService } from './sql-query.service';

@Component({
    selector: 'jhi-sql-query',
    templateUrl: './sql-query.component.html'
})
export class SQLQueryComponent implements OnInit, OnDestroy {
    sQLQueries: ISQLQuery[];
    currentAccount: any;
    eventSubscriber: Subscription;
    currentSearch: string;

    constructor(
        protected sQLQueryService: SQLQueryService,
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
            this.sQLQueryService
                .search({
                    query: this.currentSearch
                })
                .pipe(
                    filter((res: HttpResponse<ISQLQuery[]>) => res.ok),
                    map((res: HttpResponse<ISQLQuery[]>) => res.body)
                )
                .subscribe((res: ISQLQuery[]) => (this.sQLQueries = res), (res: HttpErrorResponse) => this.onError(res.message));
            return;
        }
        this.sQLQueryService
            .query()
            .pipe(
                filter((res: HttpResponse<ISQLQuery[]>) => res.ok),
                map((res: HttpResponse<ISQLQuery[]>) => res.body)
            )
            .subscribe(
                (res: ISQLQuery[]) => {
                    this.sQLQueries = res;
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
        this.registerChangeInSQLQueries();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: ISQLQuery) {
        return item.id;
    }

    registerChangeInSQLQueries() {
        this.eventSubscriber = this.eventManager.subscribe('sQLQueryListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
