import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IShipper } from 'app/shared/model/shipper.model';
import { ShipperService } from './shipper.service';

@Component({
    selector: 'jhi-shipper-delete-dialog',
    templateUrl: './shipper-delete-dialog.component.html'
})
export class ShipperDeleteDialogComponent {
    shipper: IShipper;

    constructor(protected shipperService: ShipperService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.shipperService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'shipperListModification',
                content: 'Deleted an shipper'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-shipper-delete-popup',
    template: ''
})
export class ShipperDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ shipper }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(ShipperDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.shipper = shipper;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/shipper', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/shipper', { outlets: { popup: null } }]);
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
