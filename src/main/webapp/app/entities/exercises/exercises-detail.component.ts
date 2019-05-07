import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IExercises } from 'app/shared/model/exercises.model';

@Component({
    selector: 'jhi-exercises-detail',
    templateUrl: './exercises-detail.component.html'
})
export class ExercisesDetailComponent implements OnInit {
    exercises: IExercises;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ exercises }) => {
            this.exercises = exercises;
        });
    }

    previousState() {
        window.history.back();
    }
}
