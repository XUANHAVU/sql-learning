import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { LearnSqlSharedModule } from 'app/shared';
import {
    ShipperComponent,
    ShipperDetailComponent,
    ShipperUpdateComponent,
    ShipperDeletePopupComponent,
    ShipperDeleteDialogComponent,
    shipperRoute,
    shipperPopupRoute
} from './';

const ENTITY_STATES = [...shipperRoute, ...shipperPopupRoute];

@NgModule({
    imports: [LearnSqlSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        ShipperComponent,
        ShipperDetailComponent,
        ShipperUpdateComponent,
        ShipperDeleteDialogComponent,
        ShipperDeletePopupComponent
    ],
    entryComponents: [ShipperComponent, ShipperUpdateComponent, ShipperDeleteDialogComponent, ShipperDeletePopupComponent],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class LearnSqlShipperModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
