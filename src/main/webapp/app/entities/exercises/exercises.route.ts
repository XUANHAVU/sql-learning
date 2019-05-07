import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Exercises } from 'app/shared/model/exercises.model';
import { ExercisesService } from './exercises.service';
import { ExercisesComponent } from './exercises.component';
import { ExercisesDetailComponent } from './exercises-detail.component';
import { ExercisesUpdateComponent } from './exercises-update.component';
import { ExercisesDeletePopupComponent } from './exercises-delete-dialog.component';
import { IExercises } from 'app/shared/model/exercises.model';

@Injectable({ providedIn: 'root' })
export class ExercisesResolve implements Resolve<IExercises> {
    constructor(private service: ExercisesService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IExercises> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Exercises>) => response.ok),
                map((exercises: HttpResponse<Exercises>) => exercises.body)
            );
        }
        return of(new Exercises());
    }
}

export const exercisesRoute: Routes = [
    {
        path: '',
        component: ExercisesComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'learnSqlApp.exercises.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: ExercisesDetailComponent,
        resolve: {
            exercises: ExercisesResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'learnSqlApp.exercises.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: ExercisesUpdateComponent,
        resolve: {
            exercises: ExercisesResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'learnSqlApp.exercises.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: ExercisesUpdateComponent,
        resolve: {
            exercises: ExercisesResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'learnSqlApp.exercises.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const exercisesPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: ExercisesDeletePopupComponent,
        resolve: {
            exercises: ExercisesResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'learnSqlApp.exercises.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
