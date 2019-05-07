/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { LearnSqlTestModule } from '../../../test.module';
import { SQLQueryDetailComponent } from 'app/entities/sql-query/sql-query-detail.component';
import { SQLQuery } from 'app/shared/model/sql-query.model';

describe('Component Tests', () => {
    describe('SQLQuery Management Detail Component', () => {
        let comp: SQLQueryDetailComponent;
        let fixture: ComponentFixture<SQLQueryDetailComponent>;
        const route = ({ data: of({ sQLQuery: new SQLQuery(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [LearnSqlTestModule],
                declarations: [SQLQueryDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(SQLQueryDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(SQLQueryDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.sQLQuery).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
