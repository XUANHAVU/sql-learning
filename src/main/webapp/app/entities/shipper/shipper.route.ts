import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Shipper } from 'app/shared/model/shipper.model';
import { ShipperService } from './shipper.service';
import { ShipperComponent } from './shipper.component';
import { ShipperDetailComponent } from './shipper-detail.component';
import { ShipperUpdateComponent } from './shipper-update.component';
import { ShipperDeletePopupComponent } from './shipper-delete-dialog.component';
import { IShipper } from 'app/shared/model/shipper.model';

@Injectable({ providedIn: 'root' })
export class ShipperResolve implements Resolve<IShipper> {
    constructor(private service: ShipperService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IShipper> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Shipper>) => response.ok),
                map((shipper: HttpResponse<Shipper>) => shipper.body)
            );
        }
        return of(new Shipper());
    }
}

export const shipperRoute: Routes = [
    {
        path: '',
        component: ShipperComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'learnSqlApp.shipper.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: ShipperDetailComponent,
        resolve: {
            shipper: ShipperResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'learnSqlApp.shipper.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: ShipperUpdateComponent,
        resolve: {
            shipper: ShipperResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'learnSqlApp.shipper.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: ShipperUpdateComponent,
        resolve: {
            shipper: ShipperResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'learnSqlApp.shipper.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const shipperPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: ShipperDeletePopupComponent,
        resolve: {
            shipper: ShipperResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'learnSqlApp.shipper.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
