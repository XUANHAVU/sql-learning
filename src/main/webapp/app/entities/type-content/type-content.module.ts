import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { LearnSqlSharedModule } from 'app/shared';
import {
    TypeContentComponent,
    TypeContentDetailComponent,
    TypeContentUpdateComponent,
    TypeContentDeletePopupComponent,
    TypeContentDeleteDialogComponent,
    typeContentRoute,
    typeContentPopupRoute
} from './';

const ENTITY_STATES = [...typeContentRoute, ...typeContentPopupRoute];

@NgModule({
    imports: [LearnSqlSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        TypeContentComponent,
        TypeContentDetailComponent,
        TypeContentUpdateComponent,
        TypeContentDeleteDialogComponent,
        TypeContentDeletePopupComponent
    ],
    entryComponents: [TypeContentComponent, TypeContentUpdateComponent, TypeContentDeleteDialogComponent, TypeContentDeletePopupComponent],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class LearnSqlTypeContentModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
