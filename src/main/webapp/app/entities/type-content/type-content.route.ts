import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { TypeContent } from 'app/shared/model/type-content.model';
import { TypeContentService } from './type-content.service';
import { TypeContentComponent } from './type-content.component';
import { TypeContentDetailComponent } from './type-content-detail.component';
import { TypeContentUpdateComponent } from './type-content-update.component';
import { TypeContentDeletePopupComponent } from './type-content-delete-dialog.component';
import { ITypeContent } from 'app/shared/model/type-content.model';

@Injectable({ providedIn: 'root' })
export class TypeContentResolve implements Resolve<ITypeContent> {
    constructor(private service: TypeContentService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ITypeContent> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<TypeContent>) => response.ok),
                map((typeContent: HttpResponse<TypeContent>) => typeContent.body)
            );
        }
        return of(new TypeContent());
    }
}

export const typeContentRoute: Routes = [
    {
        path: '',
        component: TypeContentComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'learnSqlApp.typeContent.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: TypeContentDetailComponent,
        resolve: {
            typeContent: TypeContentResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'learnSqlApp.typeContent.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: TypeContentUpdateComponent,
        resolve: {
            typeContent: TypeContentResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'learnSqlApp.typeContent.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: TypeContentUpdateComponent,
        resolve: {
            typeContent: TypeContentResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'learnSqlApp.typeContent.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const typeContentPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: TypeContentDeletePopupComponent,
        resolve: {
            typeContent: TypeContentResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'learnSqlApp.typeContent.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
