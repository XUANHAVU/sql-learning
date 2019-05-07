import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IExercises } from 'app/shared/model/exercises.model';
import { AccountService } from 'app/core';
import { ExercisesService } from './exercises.service';

@Component({
    selector: 'jhi-exercises',
    templateUrl: './exercises.component.html'
})
export class ExercisesComponent implements OnInit, OnDestroy {
    exercises: IExercises[];
    currentAccount: any;
    eventSubscriber: Subscription;
    currentSearch: string;

    constructor(
        protected exercisesService: ExercisesService,
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
            this.exercisesService
                .search({
                    query: this.currentSearch
                })
                .pipe(
                    filter((res: HttpResponse<IExercises[]>) => res.ok),
                    map((res: HttpResponse<IExercises[]>) => res.body)
                )
                .subscribe((res: IExercises[]) => (this.exercises = res), (res: HttpErrorResponse) => this.onError(res.message));
            return;
        }
        this.exercisesService
            .query()
            .pipe(
                filter((res: HttpResponse<IExercises[]>) => res.ok),
                map((res: HttpResponse<IExercises[]>) => res.body)
            )
            .subscribe(
                (res: IExercises[]) => {
                    this.exercises = res;
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
        this.registerChangeInExercises();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IExercises) {
        return item.id;
    }

    registerChangeInExercises() {
        this.eventSubscriber = this.eventManager.subscribe('exercisesListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
