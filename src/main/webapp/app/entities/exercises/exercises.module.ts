import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { LearnSqlSharedModule } from 'app/shared';
import {
    ExercisesComponent,
    ExercisesDetailComponent,
    ExercisesUpdateComponent,
    ExercisesDeletePopupComponent,
    ExercisesDeleteDialogComponent,
    exercisesRoute,
    exercisesPopupRoute
} from './';

const ENTITY_STATES = [...exercisesRoute, ...exercisesPopupRoute];

@NgModule({
    imports: [LearnSqlSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        ExercisesComponent,
        ExercisesDetailComponent,
        ExercisesUpdateComponent,
        ExercisesDeleteDialogComponent,
        ExercisesDeletePopupComponent
    ],
    entryComponents: [ExercisesComponent, ExercisesUpdateComponent, ExercisesDeleteDialogComponent, ExercisesDeletePopupComponent],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class LearnSqlExercisesModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
