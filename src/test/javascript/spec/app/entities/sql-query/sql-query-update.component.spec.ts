/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { LearnSqlTestModule } from '../../../test.module';
import { SQLQueryUpdateComponent } from 'app/entities/sql-query/sql-query-update.component';
import { SQLQueryService } from 'app/entities/sql-query/sql-query.service';
import { SQLQuery } from 'app/shared/model/sql-query.model';

describe('Component Tests', () => {
    describe('SQLQuery Management Update Component', () => {
        let comp: SQLQueryUpdateComponent;
        let fixture: ComponentFixture<SQLQueryUpdateComponent>;
        let service: SQLQueryService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [LearnSqlTestModule],
                declarations: [SQLQueryUpdateComponent]
            })
                .overrideTemplate(SQLQueryUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(SQLQueryUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SQLQueryService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new SQLQuery(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.sQLQuery = entity;
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
                    const entity = new SQLQuery();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.sQLQuery = entity;
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
