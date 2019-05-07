/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { LearnSqlTestModule } from '../../../test.module';
import { ExercisesDeleteDialogComponent } from 'app/entities/exercises/exercises-delete-dialog.component';
import { ExercisesService } from 'app/entities/exercises/exercises.service';

describe('Component Tests', () => {
    describe('Exercises Management Delete Component', () => {
        let comp: ExercisesDeleteDialogComponent;
        let fixture: ComponentFixture<ExercisesDeleteDialogComponent>;
        let service: ExercisesService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [LearnSqlTestModule],
                declarations: [ExercisesDeleteDialogComponent]
            })
                .overrideTemplate(ExercisesDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ExercisesDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ExercisesService);
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
