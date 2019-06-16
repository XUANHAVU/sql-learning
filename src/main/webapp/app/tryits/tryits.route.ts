import {Routes} from '@angular/router';
import {TryitsComponent} from 'app/tryits/tryits.component';

export const TRYIT_ROUTE: Routes = [
    {
        path: 'try-it',
        component: TryitsComponent,
        data: {
            authorities: [],
            pageTitle: 'home.title'
        }
    }
];
