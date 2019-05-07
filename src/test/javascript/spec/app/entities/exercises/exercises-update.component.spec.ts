/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { LearnSqlTestModule } from '../../../test.module';
import { ExercisesUpdateComponent } from 'app/entities/exercises/exercises-update.component';
import { ExercisesService } from 'app/entities/exercises/exercises.service';
import { Exercises } from 'app/shared/model/exercises.model';

describe('Component Tests', () => {
    describe('Exercises Management Update Component', () => {
        let comp: ExercisesUpdateComponent;
        let fixture: ComponentFixture<ExercisesUpdateComponent>;
        let service: ExercisesService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [LearnSqlTestModule],
                declarations: [ExercisesUpdateComponent]
            })
                .overrideTemplate(ExercisesUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ExercisesUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ExercisesService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Exercises(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.exercises = entity;
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
                    const entity = new Exercises();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.exercises = entity;
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
