/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { LearnSqlTestModule } from '../../../test.module';
import { ExercisesDetailComponent } from 'app/entities/exercises/exercises-detail.component';
import { Exercises } from 'app/shared/model/exercises.model';

describe('Component Tests', () => {
    describe('Exercises Management Detail Component', () => {
        let comp: ExercisesDetailComponent;
        let fixture: ComponentFixture<ExercisesDetailComponent>;
        const route = ({ data: of({ exercises: new Exercises(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [LearnSqlTestModule],
                declarations: [ExercisesDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(ExercisesDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ExercisesDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.exercises).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
