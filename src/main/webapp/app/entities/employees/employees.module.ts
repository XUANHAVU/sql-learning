import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { LearnSqlSharedModule } from 'app/shared';
import {
    EmployeesComponent,
    EmployeesDetailComponent,
    EmployeesUpdateComponent,
    EmployeesDeletePopupComponent,
    EmployeesDeleteDialogComponent,
    employeesRoute,
    employeesPopupRoute
} from './';

const ENTITY_STATES = [...employeesRoute, ...employeesPopupRoute];

@NgModule({
    imports: [LearnSqlSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        EmployeesComponent,
        EmployeesDetailComponent,
        EmployeesUpdateComponent,
        EmployeesDeleteDialogComponent,
        EmployeesDeletePopupComponent
    ],
    entryComponents: [EmployeesComponent, EmployeesUpdateComponent, EmployeesDeleteDialogComponent, EmployeesDeletePopupComponent],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class LearnSqlEmployeesModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
