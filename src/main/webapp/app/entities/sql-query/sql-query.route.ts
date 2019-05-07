import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { SQLQuery } from 'app/shared/model/sql-query.model';
import { SQLQueryService } from './sql-query.service';
import { SQLQueryComponent } from './sql-query.component';
import { SQLQueryDetailComponent } from './sql-query-detail.component';
import { SQLQueryUpdateComponent } from './sql-query-update.component';
import { SQLQueryDeletePopupComponent } from './sql-query-delete-dialog.component';
import { ISQLQuery } from 'app/shared/model/sql-query.model';

@Injectable({ providedIn: 'root' })
export class SQLQueryResolve implements Resolve<ISQLQuery> {
    constructor(private service: SQLQueryService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ISQLQuery> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<SQLQuery>) => response.ok),
                map((sQLQuery: HttpResponse<SQLQuery>) => sQLQuery.body)
            );
        }
        return of(new SQLQuery());
    }
}

export const sQLQueryRoute: Routes = [
    {
        path: '',
        component: SQLQueryComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'learnSqlApp.sQLQuery.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: SQLQueryDetailComponent,
        resolve: {
            sQLQuery: SQLQueryResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'learnSqlApp.sQLQuery.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: SQLQueryUpdateComponent,
        resolve: {
            sQLQuery: SQLQueryResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'learnSqlApp.sQLQuery.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: SQLQueryUpdateComponent,
        resolve: {
            sQLQuery: SQLQueryResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'learnSqlApp.sQLQuery.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const sQLQueryPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: SQLQueryDeletePopupComponent,
        resolve: {
            sQLQuery: SQLQueryResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'learnSqlApp.sQLQuery.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
