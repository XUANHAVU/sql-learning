import { Route, Routes } from '@angular/router';

import { HomeComponent } from './';
import {CategoryComponent, CategoryResolve} from 'app/entities/category';
// import {UserRouteAccessService} from "app/core";

// export const HOME_ROUTE: Route = {
//     path: '',
//     component: HomeComponent,
//     data: {
//         authorities: [],
//         pageTitle: 'home.title'
//     }
// };

export const HOME_ROUTE: Routes = [
    {
        path: '',
        component: HomeComponent,
        data: {
            authorities: [],
            pageTitle: 'home.title'
        }
    },
    {
        path: 'view/:name/:id',
        component: HomeComponent,
        resolve: {
            category: CategoryResolve
        },
        data: {
            authorities: [],
            pageTitle: 'home.title'
        }
    }
];
