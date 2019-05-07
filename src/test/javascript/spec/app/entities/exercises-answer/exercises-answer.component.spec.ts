/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { LearnSqlTestModule } from '../../../test.module';
import { ExercisesAnswerComponent } from 'app/entities/exercises-answer/exercises-answer.component';
import { ExercisesAnswerService } from 'app/entities/exercises-answer/exercises-answer.service';
import { ExercisesAnswer } from 'app/shared/model/exercises-answer.model';

describe('Component Tests', () => {
    describe('ExercisesAnswer Management Component', () => {
        let comp: ExercisesAnswerComponent;
        let fixture: ComponentFixture<ExercisesAnswerComponent>;
        let service: ExercisesAnswerService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [LearnSqlTestModule],
                declarations: [ExercisesAnswerComponent],
                providers: []
            })
                .overrideTemplate(ExercisesAnswerComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ExercisesAnswerComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ExercisesAnswerService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new ExercisesAnswer(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.exercisesAnswers[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
