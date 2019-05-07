/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { LearnSqlTestModule } from '../../../test.module';
import { TypeContentDeleteDialogComponent } from 'app/entities/type-content/type-content-delete-dialog.component';
import { TypeContentService } from 'app/entities/type-content/type-content.service';

describe('Component Tests', () => {
    describe('TypeContent Management Delete Component', () => {
        let comp: TypeContentDeleteDialogComponent;
        let fixture: ComponentFixture<TypeContentDeleteDialogComponent>;
        let service: TypeContentService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [LearnSqlTestModule],
                declarations: [TypeContentDeleteDialogComponent]
            })
                .overrideTemplate(TypeContentDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(TypeContentDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TypeContentService);
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
