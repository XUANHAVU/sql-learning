import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IExercisesAnswer } from 'app/shared/model/exercises-answer.model';
import { ExercisesAnswerService } from './exercises-answer.service';

@Component({
    selector: 'jhi-exercises-answer-delete-dialog',
    templateUrl: './exercises-answer-delete-dialog.component.html'
})
export class ExercisesAnswerDeleteDialogComponent {
    exercisesAnswer: IExercisesAnswer;

    constructor(
        protected exercisesAnswerService: ExercisesAnswerService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.exercisesAnswerService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'exercisesAnswerListModification',
                content: 'Deleted an exercisesAnswer'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-exercises-answer-delete-popup',
    template: ''
})
export class ExercisesAnswerDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ exercisesAnswer }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(ExercisesAnswerDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.exercisesAnswer = exercisesAnswer;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/exercises-answer', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/exercises-answer', { outlets: { popup: null } }]);
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
