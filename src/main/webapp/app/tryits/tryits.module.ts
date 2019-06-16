import {NgModule, CUSTOM_ELEMENTS_SCHEMA} from '@angular/core';
import {RouterModule} from '@angular/router';

import {LearnSqlSharedModule} from 'app/shared';
import {MDBBootstrapModule} from 'angular-bootstrap-md';
import {TRYIT_ROUTE} from 'app/tryits/tryits.route';
import {TryitsComponent} from 'app/tryits/tryits.component';
import {KeysPipe} from 'app/layouts/safe-pipe';

@NgModule({
    imports: [LearnSqlSharedModule, RouterModule.forChild(TRYIT_ROUTE), MDBBootstrapModule.forRoot()],
    declarations: [TryitsComponent, KeysPipe],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class LearnSqlTryItModule {
}
