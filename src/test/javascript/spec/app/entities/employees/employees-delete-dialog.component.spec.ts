/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { LearnSqlTestModule } from '../../../test.module';
import { EmployeesDeleteDialogComponent } from 'app/entities/employees/employees-delete-dialog.component';
import { EmployeesService } from 'app/entities/employees/employees.service';

describe('Component Tests', () => {
    describe('Employees Management Delete Component', () => {
        let comp: EmployeesDeleteDialogComponent;
        let fixture: ComponentFixture<EmployeesDeleteDialogComponent>;
        let service: EmployeesService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [LearnSqlTestModule],
                declarations: [EmployeesDeleteDialogComponent]
            })
                .overrideTemplate(EmployeesDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(EmployeesDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(EmployeesService);
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
