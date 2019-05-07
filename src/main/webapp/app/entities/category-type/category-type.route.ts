import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { CategoryType } from 'app/shared/model/category-type.model';
import { CategoryTypeService } from './category-type.service';
import { CategoryTypeComponent } from './category-type.component';
import { CategoryTypeDetailComponent } from './category-type-detail.component';
import { CategoryTypeUpdateComponent } from './category-type-update.component';
import { CategoryTypeDeletePopupComponent } from './category-type-delete-dialog.component';
import { ICategoryType } from 'app/shared/model/category-type.model';

@Injectable({ providedIn: 'root' })
export class CategoryTypeResolve implements Resolve<ICategoryType> {
    constructor(private service: CategoryTypeService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ICategoryType> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<CategoryType>) => response.ok),
                map((categoryType: HttpResponse<CategoryType>) => categoryType.body)
            );
        }
        return of(new CategoryType());
    }
}

export const categoryTypeRoute: Routes = [
    {
        path: '',
        component: CategoryTypeComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'learnSqlApp.categoryType.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: CategoryTypeDetailComponent,
        resolve: {
            categoryType: CategoryTypeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'learnSqlApp.categoryType.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: CategoryTypeUpdateComponent,
        resolve: {
            categoryType: CategoryTypeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'learnSqlApp.categoryType.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: CategoryTypeUpdateComponent,
        resolve: {
            categoryType: CategoryTypeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'learnSqlApp.categoryType.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const categoryTypePopupRoute: Routes = [
    {
        path: ':id/delete',
        component: CategoryTypeDeletePopupComponent,
        resolve: {
            categoryType: CategoryTypeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'learnSqlApp.categoryType.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
