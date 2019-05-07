import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { LearnSqlSharedModule } from 'app/shared';
import {
    CategoryTypeComponent,
    CategoryTypeDetailComponent,
    CategoryTypeUpdateComponent,
    CategoryTypeDeletePopupComponent,
    CategoryTypeDeleteDialogComponent,
    categoryTypeRoute,
    categoryTypePopupRoute
} from './';

const ENTITY_STATES = [...categoryTypeRoute, ...categoryTypePopupRoute];

@NgModule({
    imports: [LearnSqlSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        CategoryTypeComponent,
        CategoryTypeDetailComponent,
        CategoryTypeUpdateComponent,
        CategoryTypeDeleteDialogComponent,
        CategoryTypeDeletePopupComponent
    ],
    entryComponents: [
        CategoryTypeComponent,
        CategoryTypeUpdateComponent,
        CategoryTypeDeleteDialogComponent,
        CategoryTypeDeletePopupComponent
    ],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class LearnSqlCategoryTypeModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
