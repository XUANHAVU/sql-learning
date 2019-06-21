import {Routes} from '@angular/router';
import {DashboardComponent} from './dashboard.component';

export const Dashboard_ROUTE: Routes = [
    {
        path: 'dashboard',
        component: DashboardComponent,
        data: {
            authorities: [],
            pageTitle: 'home.title'
        }
    }
];
