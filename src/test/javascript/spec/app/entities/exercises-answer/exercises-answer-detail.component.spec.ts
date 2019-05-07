/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { LearnSqlTestModule } from '../../../test.module';
import { ExercisesAnswerDetailComponent } from 'app/entities/exercises-answer/exercises-answer-detail.component';
import { ExercisesAnswer } from 'app/shared/model/exercises-answer.model';

describe('Component Tests', () => {
    describe('ExercisesAnswer Management Detail Component', () => {
        let comp: ExercisesAnswerDetailComponent;
        let fixture: ComponentFixture<ExercisesAnswerDetailComponent>;
        const route = ({ data: of({ exercisesAnswer: new ExercisesAnswer(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [LearnSqlTestModule],
                declarations: [ExercisesAnswerDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(ExercisesAnswerDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ExercisesAnswerDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.exercisesAnswer).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
