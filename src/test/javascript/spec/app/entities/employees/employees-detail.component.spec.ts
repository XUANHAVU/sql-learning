/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { LearnSqlTestModule } from '../../../test.module';
import { EmployeesDetailComponent } from 'app/entities/employees/employees-detail.component';
import { Employees } from 'app/shared/model/employees.model';

describe('Component Tests', () => {
    describe('Employees Management Detail Component', () => {
        let comp: EmployeesDetailComponent;
        let fixture: ComponentFixture<EmployeesDetailComponent>;
        const route = ({ data: of({ employees: new Employees(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [LearnSqlTestModule],
                declarations: [EmployeesDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(EmployeesDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(EmployeesDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.employees).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
