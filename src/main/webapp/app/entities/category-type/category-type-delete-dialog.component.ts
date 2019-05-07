import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICategoryType } from 'app/shared/model/category-type.model';
import { CategoryTypeService } from './category-type.service';

@Component({
    selector: 'jhi-category-type-delete-dialog',
    templateUrl: './category-type-delete-dialog.component.html'
})
export class CategoryTypeDeleteDialogComponent {
    categoryType: ICategoryType;

    constructor(
        protected categoryTypeService: CategoryTypeService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.categoryTypeService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'categoryTypeListModification',
                content: 'Deleted an categoryType'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-category-type-delete-popup',
    template: ''
})
export class CategoryTypeDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ categoryType }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(CategoryTypeDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.categoryType = categoryType;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/category-type', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/category-type', { outlets: { popup: null } }]);
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
