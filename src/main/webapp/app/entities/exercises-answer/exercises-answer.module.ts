import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { LearnSqlSharedModule } from 'app/shared';
import {
    ExercisesAnswerComponent,
    ExercisesAnswerDetailComponent,
    ExercisesAnswerUpdateComponent,
    ExercisesAnswerDeletePopupComponent,
    ExercisesAnswerDeleteDialogComponent,
    exercisesAnswerRoute,
    exercisesAnswerPopupRoute
} from './';
import {MDBBootstrapModule} from 'angular-bootstrap-md';

const ENTITY_STATES = [...exercisesAnswerRoute, ...exercisesAnswerPopupRoute];

@NgModule({
    imports: [LearnSqlSharedModule, RouterModule.forChild(ENTITY_STATES), MDBBootstrapModule.forRoot()],
    declarations: [
        ExercisesAnswerComponent,
        ExercisesAnswerDetailComponent,
        ExercisesAnswerUpdateComponent,
        ExercisesAnswerDeleteDialogComponent,
        ExercisesAnswerDeletePopupComponent
    ],
    entryComponents: [
        ExercisesAnswerComponent,
        ExercisesAnswerUpdateComponent,
        ExercisesAnswerDeleteDialogComponent,
        ExercisesAnswerDeletePopupComponent
    ],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class LearnSqlExercisesAnswerModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
