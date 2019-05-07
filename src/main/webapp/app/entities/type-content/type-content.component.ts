import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ITypeContent } from 'app/shared/model/type-content.model';
import { AccountService } from 'app/core';
import { TypeContentService } from './type-content.service';

@Component({
    selector: 'jhi-type-content',
    templateUrl: './type-content.component.html'
})
export class TypeContentComponent implements OnInit, OnDestroy {
    typeContents: ITypeContent[];
    currentAccount: any;
    eventSubscriber: Subscription;
    currentSearch: string;

    constructor(
        protected typeContentService: TypeContentService,
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
            this.typeContentService
                .search({
                    query: this.currentSearch
                })
                .pipe(
                    filter((res: HttpResponse<ITypeContent[]>) => res.ok),
                    map((res: HttpResponse<ITypeContent[]>) => res.body)
                )
                .subscribe((res: ITypeContent[]) => (this.typeContents = res), (res: HttpErrorResponse) => this.onError(res.message));
            return;
        }
        this.typeContentService
            .query()
            .pipe(
                filter((res: HttpResponse<ITypeContent[]>) => res.ok),
                map((res: HttpResponse<ITypeContent[]>) => res.body)
            )
            .subscribe(
                (res: ITypeContent[]) => {
                    this.typeContents = res;
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
        this.registerChangeInTypeContents();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: ITypeContent) {
        return item.id;
    }

    registerChangeInTypeContents() {
        this.eventSubscriber = this.eventManager.subscribe('typeContentListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
