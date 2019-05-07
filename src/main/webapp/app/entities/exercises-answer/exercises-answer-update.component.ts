import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';
import { IExercisesAnswer } from 'app/shared/model/exercises-answer.model';
import { ExercisesAnswerService } from './exercises-answer.service';
import { IExercises } from 'app/shared/model/exercises.model';
import { ExercisesService } from 'app/entities/exercises';

@Component({
    selector: 'jhi-exercises-answer-update',
    templateUrl: './exercises-answer-update.component.html'
})
export class ExercisesAnswerUpdateComponent implements OnInit {
    exercisesAnswer: IExercisesAnswer;
    isSaving: boolean;

    exercises: IExercises[];
    createdDate: string;
    updatedDate: string;

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected exercisesAnswerService: ExercisesAnswerService,
        protected exercisesService: ExercisesService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ exercisesAnswer }) => {
            this.exercisesAnswer = exercisesAnswer;
            this.createdDate = this.exercisesAnswer.createdDate != null ? this.exercisesAnswer.createdDate.format(DATE_TIME_FORMAT) : null;
            this.updatedDate = this.exercisesAnswer.updatedDate != null ? this.exercisesAnswer.updatedDate.format(DATE_TIME_FORMAT) : null;
        });
        this.exercisesService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IExercises[]>) => mayBeOk.ok),
                map((response: HttpResponse<IExercises[]>) => response.body)
            )
            .subscribe((res: IExercises[]) => (this.exercises = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        this.exercisesAnswer.createdDate = this.createdDate != null ? moment(this.createdDate, DATE_TIME_FORMAT) : null;
        this.exercisesAnswer.updatedDate = this.updatedDate != null ? moment(this.updatedDate, DATE_TIME_FORMAT) : null;
        if (this.exercisesAnswer.id !== undefined) {
            this.subscribeToSaveResponse(this.exercisesAnswerService.update(this.exercisesAnswer));
        } else {
            this.subscribeToSaveResponse(this.exercisesAnswerService.create(this.exercisesAnswer));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IExercisesAnswer>>) {
        result.subscribe((res: HttpResponse<IExercisesAnswer>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackExercisesById(index: number, item: IExercises) {
        return item.id;
    }
}
