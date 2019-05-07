/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { LearnSqlTestModule } from '../../../test.module';
import { SQLQueryDeleteDialogComponent } from 'app/entities/sql-query/sql-query-delete-dialog.component';
import { SQLQueryService } from 'app/entities/sql-query/sql-query.service';

describe('Component Tests', () => {
    describe('SQLQuery Management Delete Component', () => {
        let comp: SQLQueryDeleteDialogComponent;
        let fixture: ComponentFixture<SQLQueryDeleteDialogComponent>;
        let service: SQLQueryService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [LearnSqlTestModule],
                declarations: [SQLQueryDeleteDialogComponent]
            })
                .overrideTemplate(SQLQueryDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(SQLQueryDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SQLQueryService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete', inject(
                [],
                fakeAsync(() => {
                    // GIVEN
                    spyOn(service, 'delete').and.returnValue(of({}));

                    // WHEN
                    comp.confirmDelete(123);
                    tick();

                    // THEN
                    expect(service.delete).toHaveBeenCalledWith(123);
                    expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                })
            ));
        });
    });
});
