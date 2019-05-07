import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Employees } from 'app/shared/model/employees.model';
import { EmployeesService } from './employees.service';
import { EmployeesComponent } from './employees.component';
import { EmployeesDetailComponent } from './employees-detail.component';
import { EmployeesUpdateComponent } from './employees-update.component';
import { EmployeesDeletePopupComponent } from './employees-delete-dialog.component';
import { IEmployees } from 'app/shared/model/employees.model';

@Injectable({ providedIn: 'root' })
export class EmployeesResolve implements Resolve<IEmployees> {
    constructor(private service: EmployeesService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IEmployees> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Employees>) => response.ok),
                map((employees: HttpResponse<Employees>) => employees.body)
            );
        }
        return of(new Employees());
    }
}

export const employeesRoute: Routes = [
    {
        path: '',
        component: EmployeesComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'learnSqlApp.employees.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: EmployeesDetailComponent,
        resolve: {
            employees: EmployeesResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'learnSqlApp.employees.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: EmployeesUpdateComponent,
        resolve: {
            employees: EmployeesResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'learnSqlApp.employees.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: EmployeesUpdateComponent,
        resolve: {
            employees: EmployeesResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'learnSqlApp.employees.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const employeesPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: EmployeesDeletePopupComponent,
        resolve: {
            employees: EmployeesResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'learnSqlApp.employees.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
