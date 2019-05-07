/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { LearnSqlTestModule } from '../../../test.module';
import { ExercisesAnswerUpdateComponent } from 'app/entities/exercises-answer/exercises-answer-update.component';
import { ExercisesAnswerService } from 'app/entities/exercises-answer/exercises-answer.service';
import { ExercisesAnswer } from 'app/shared/model/exercises-answer.model';

describe('Component Tests', () => {
    describe('ExercisesAnswer Management Update Component', () => {
        let comp: ExercisesAnswerUpdateComponent;
        let fixture: ComponentFixture<ExercisesAnswerUpdateComponent>;
        let service: ExercisesAnswerService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [LearnSqlTestModule],
                declarations: [ExercisesAnswerUpdateComponent]
            })
                .overrideTemplate(ExercisesAnswerUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ExercisesAnswerUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ExercisesAnswerService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new ExercisesAnswer(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.exercisesAnswer = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.update).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );

            it(
                'Should call create service on save for new entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new ExercisesAnswer();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.exercisesAnswer = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.create).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );
        });
    });
});
