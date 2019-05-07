/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { LearnSqlTestModule } from '../../../test.module';
import { CategoryTypeDeleteDialogComponent } from 'app/entities/category-type/category-type-delete-dialog.component';
import { CategoryTypeService } from 'app/entities/category-type/category-type.service';

describe('Component Tests', () => {
    describe('CategoryType Management Delete Component', () => {
        let comp: CategoryTypeDeleteDialogComponent;
        let fixture: ComponentFixture<CategoryTypeDeleteDialogComponent>;
        let service: CategoryTypeService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [LearnSqlTestModule],
                declarations: [CategoryTypeDeleteDialogComponent]
            })
                .overrideTemplate(CategoryTypeDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(CategoryTypeDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CategoryTypeService);
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
