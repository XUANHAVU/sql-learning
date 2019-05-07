import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ExercisesAnswer } from 'app/shared/model/exercises-answer.model';
import { ExercisesAnswerService } from './exercises-answer.service';
import { ExercisesAnswerComponent } from './exercises-answer.component';
import { ExercisesAnswerDetailComponent } from './exercises-answer-detail.component';
import { ExercisesAnswerUpdateComponent } from './exercises-answer-update.component';
import { ExercisesAnswerDeletePopupComponent } from './exercises-answer-delete-dialog.component';
import { IExercisesAnswer } from 'app/shared/model/exercises-answer.model';

@Injectable({ providedIn: 'root' })
export class ExercisesAnswerResolve implements Resolve<IExercisesAnswer> {
    constructor(private service: ExercisesAnswerService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IExercisesAnswer> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<ExercisesAnswer>) => response.ok),
                map((exercisesAnswer: HttpResponse<ExercisesAnswer>) => exercisesAnswer.body)
            );
        }
        return of(new ExercisesAnswer());
    }
}

export const exercisesAnswerRoute: Routes = [
    {
        path: '',
        component: ExercisesAnswerComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'learnSqlApp.exercisesAnswer.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: ExercisesAnswerDetailComponent,
        resolve: {
            exercisesAnswer: ExercisesAnswerResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'learnSqlApp.exercisesAnswer.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: ExercisesAnswerUpdateComponent,
        resolve: {
            exercisesAnswer: ExercisesAnswerResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'learnSqlApp.exercisesAnswer.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: ExercisesAnswerUpdateComponent,
        resolve: {
            exercisesAnswer: ExercisesAnswerResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'learnSqlApp.exercisesAnswer.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const exercisesAnswerPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: ExercisesAnswerDeletePopupComponent,
        resolve: {
            exercisesAnswer: ExercisesAnswerResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'learnSqlApp.exercisesAnswer.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
