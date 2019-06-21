import {NgModule, CUSTOM_ELEMENTS_SCHEMA} from '@angular/core';
import {RouterModule} from '@angular/router';

import {LearnSqlSharedModule} from 'app/shared';
import {MDBBootstrapModule} from 'angular-bootstrap-md';
import {DashboardComponent} from './dashboard.component';
import {Dashboard_ROUTE} from './dashboard.route';

@NgModule({
    imports: [LearnSqlSharedModule, RouterModule.forChild(Dashboard_ROUTE), MDBBootstrapModule.forRoot()],
    declarations: [DashboardComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class LearnSqlDashBoardModule {
}
