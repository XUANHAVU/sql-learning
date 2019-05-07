/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { LearnSqlTestModule } from '../../../test.module';
import { SQLQueryComponent } from 'app/entities/sql-query/sql-query.component';
import { SQLQueryService } from 'app/entities/sql-query/sql-query.service';
import { SQLQuery } from 'app/shared/model/sql-query.model';

describe('Component Tests', () => {
    describe('SQLQuery Management Component', () => {
        let comp: SQLQueryComponent;
        let fixture: ComponentFixture<SQLQueryComponent>;
        let service: SQLQueryService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [LearnSqlTestModule],
                declarations: [SQLQueryComponent],
                providers: []
            })
                .overrideTemplate(SQLQueryComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(SQLQueryComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SQLQueryService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new SQLQuery(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.sQLQueries[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
