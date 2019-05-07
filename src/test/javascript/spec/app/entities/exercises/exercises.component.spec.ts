/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { LearnSqlTestModule } from '../../../test.module';
import { ExercisesComponent } from 'app/entities/exercises/exercises.component';
import { ExercisesService } from 'app/entities/exercises/exercises.service';
import { Exercises } from 'app/shared/model/exercises.model';

describe('Component Tests', () => {
    describe('Exercises Management Component', () => {
        let comp: ExercisesComponent;
        let fixture: ComponentFixture<ExercisesComponent>;
        let service: ExercisesService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [LearnSqlTestModule],
                declarations: [ExercisesComponent],
                providers: []
            })
                .overrideTemplate(ExercisesComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ExercisesComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ExercisesService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new Exercises(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.exercises[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
