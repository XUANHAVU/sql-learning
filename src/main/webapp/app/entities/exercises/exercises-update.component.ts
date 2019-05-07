import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';
import { IExercises } from 'app/shared/model/exercises.model';
import { ExercisesService } from './exercises.service';
import { ICategory } from 'app/shared/model/category.model';
import { CategoryService } from 'app/entities/category';

@Component({
    selector: 'jhi-exercises-update',
    templateUrl: './exercises-update.component.html'
})
export class ExercisesUpdateComponent implements OnInit {
    exercises: IExercises;
    isSaving: boolean;

    categories: ICategory[];
    createdDate: string;
    updatedDate: string;

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected exercisesService: ExercisesService,
        protected categoryService: CategoryService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ exercises }) => {
            this.exercises = exercises;
            this.createdDate = this.exercises.createdDate != null ? this.exercises.createdDate.format(DATE_TIME_FORMAT) : null;
            this.updatedDate = this.exercises.updatedDate != null ? this.exercises.updatedDate.format(DATE_TIME_FORMAT) : null;
        });
        this.categoryService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<ICategory[]>) => mayBeOk.ok),
                map((response: HttpResponse<ICategory[]>) => response.body)
            )
            .subscribe((res: ICategory[]) => (this.categories = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        this.exercises.createdDate = this.createdDate != null ? moment(this.createdDate, DATE_TIME_FORMAT) : null;
        this.exercises.updatedDate = this.updatedDate != null ? moment(this.updatedDate, DATE_TIME_FORMAT) : null;
        if (this.exercises.id !== undefined) {
            this.subscribeToSaveResponse(this.exercisesService.update(this.exercises));
        } else {
            this.subscribeToSaveResponse(this.exercisesService.create(this.exercises));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IExercises>>) {
        result.subscribe((res: HttpResponse<IExercises>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackCategoryById(index: number, item: ICategory) {
        return item.id;
    }
}
