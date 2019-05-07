import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IExercisesAnswer } from 'app/shared/model/exercises-answer.model';

@Component({
    selector: 'jhi-exercises-answer-detail',
    templateUrl: './exercises-answer-detail.component.html'
})
export class ExercisesAnswerDetailComponent implements OnInit {
    exercisesAnswer: IExercisesAnswer;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ exercisesAnswer }) => {
            this.exercisesAnswer = exercisesAnswer;
        });
    }

    previousState() {
        window.history.back();
    }
}
