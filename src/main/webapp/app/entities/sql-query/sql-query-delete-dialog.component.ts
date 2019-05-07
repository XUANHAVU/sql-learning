import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISQLQuery } from 'app/shared/model/sql-query.model';
import { SQLQueryService } from './sql-query.service';

@Component({
    selector: 'jhi-sql-query-delete-dialog',
    templateUrl: './sql-query-delete-dialog.component.html'
})
export class SQLQueryDeleteDialogComponent {
    sQLQuery: ISQLQuery;

    constructor(protected sQLQueryService: SQLQueryService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.sQLQueryService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'sQLQueryListModification',
                content: 'Deleted an sQLQuery'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-sql-query-delete-popup',
    template: ''
})
export class SQLQueryDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ sQLQuery }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(SQLQueryDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.sQLQuery = sQLQuery;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/sql-query', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/sql-query', { outlets: { popup: null } }]);
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
