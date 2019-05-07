import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IExercises } from 'app/shared/model/exercises.model';
import { ExercisesService } from './exercises.service';

@Component({
    selector: 'jhi-exercises-delete-dialog',
    templateUrl: './exercises-delete-dialog.component.html'
})
export class ExercisesDeleteDialogComponent {
    exercises: IExercises;

    constructor(
        protected exercisesService: ExercisesService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.exercisesService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'exercisesListModification',
                content: 'Deleted an exercises'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-exercises-delete-popup',
    template: ''
})
export class ExercisesDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ exercises }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(ExercisesDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.exercises = exercises;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/exercises', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/exercises', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    }
                );
            }, 0);
        });
    }

    ngOnDestroy() {
        this.ngbModalRef = null;
    }
}
