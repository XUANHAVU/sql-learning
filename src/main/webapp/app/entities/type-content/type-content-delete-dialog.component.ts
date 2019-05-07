import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITypeContent } from 'app/shared/model/type-content.model';
import { TypeContentService } from './type-content.service';

@Component({
    selector: 'jhi-type-content-delete-dialog',
    templateUrl: './type-content-delete-dialog.component.html'
})
export class TypeContentDeleteDialogComponent {
    typeContent: ITypeContent;

    constructor(
        protected typeContentService: TypeContentService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.typeContentService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'typeContentListModification',
                content: 'Deleted an typeContent'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-type-content-delete-popup',
    template: ''
})
export class TypeContentDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ typeContent }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(TypeContentDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.typeContent = typeContent;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/type-content', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/type-content', { outlets: { popup: null } }]);
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
