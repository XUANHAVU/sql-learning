import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { LearnSqlSharedModule } from 'app/shared';
import {
    SQLQueryComponent,
    SQLQueryDetailComponent,
    SQLQueryUpdateComponent,
    SQLQueryDeletePopupComponent,
    SQLQueryDeleteDialogComponent,
    sQLQueryRoute,
    sQLQueryPopupRoute
} from './';

const ENTITY_STATES = [...sQLQueryRoute, ...sQLQueryPopupRoute];

@NgModule({
    imports: [LearnSqlSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        SQLQueryComponent,
        SQLQueryDetailComponent,
        SQLQueryUpdateComponent,
        SQLQueryDeleteDialogComponent,
        SQLQueryDeletePopupComponent
    ],
    entryComponents: [SQLQueryComponent, SQLQueryUpdateComponent, SQLQueryDeleteDialogComponent, SQLQueryDeletePopupComponent],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class LearnSqlSQLQueryModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
