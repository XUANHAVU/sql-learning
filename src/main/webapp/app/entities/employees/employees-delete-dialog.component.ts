import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IEmployees } from 'app/shared/model/employees.model';
import { EmployeesService } from './employees.service';

@Component({
    selector: 'jhi-employees-delete-dialog',
    templateUrl: './employees-delete-dialog.component.html'
})
export class EmployeesDeleteDialogComponent {
    employees: IEmployees;

    constructor(
        protected employeesService: EmployeesService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.employeesService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'employeesListModification',
                content: 'Deleted an employees'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-employees-delete-popup',
    template: ''
})
export class EmployeesDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ employees }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(EmployeesDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.employees = employees;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/employees', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/employees', { outlets: { popup: null } }]);
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
