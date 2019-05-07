import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IEmployees } from 'app/shared/model/employees.model';
import { AccountService } from 'app/core';
import { EmployeesService } from './employees.service';

@Component({
    selector: 'jhi-employees',
    templateUrl: './employees.component.html'
})
export class EmployeesComponent implements OnInit, OnDestroy {
    employees: IEmployees[];
    currentAccount: any;
    eventSubscriber: Subscription;
    currentSearch: string;

    constructor(
        protected employeesService: EmployeesService,
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
            this.employeesService
                .search({
                    query: this.currentSearch
                })
                .pipe(
                    filter((res: HttpResponse<IEmployees[]>) => res.ok),
                    map((res: HttpResponse<IEmployees[]>) => res.body)
                )
                .subscribe((res: IEmployees[]) => (this.employees = res), (res: HttpErrorResponse) => this.onError(res.message));
            return;
        }
        this.employeesService
            .query()
            .pipe(
                filter((res: HttpResponse<IEmployees[]>) => res.ok),
                map((res: HttpResponse<IEmployees[]>) => res.body)
            )
            .subscribe(
                (res: IEmployees[]) => {
                    this.employees = res;
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
        this.registerChangeInEmployees();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IEmployees) {
        return item.id;
    }

    registerChangeInEmployees() {
        this.eventSubscriber = this.eventManager.subscribe('employeesListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
