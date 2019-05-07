import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { IEmployees } from 'app/shared/model/employees.model';
import { EmployeesService } from './employees.service';

@Component({
    selector: 'jhi-employees-update',
    templateUrl: './employees-update.component.html'
})
export class EmployeesUpdateComponent implements OnInit {
    employees: IEmployees;
    isSaving: boolean;
    birthDate: string;

    constructor(protected employeesService: EmployeesService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ employees }) => {
            this.employees = employees;
            this.birthDate = this.employees.birthDate != null ? this.employees.birthDate.format(DATE_TIME_FORMAT) : null;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        this.employees.birthDate = this.birthDate != null ? moment(this.birthDate, DATE_TIME_FORMAT) : null;
        if (this.employees.id !== undefined) {
            this.subscribeToSaveResponse(this.employeesService.update(this.employees));
        } else {
            this.subscribeToSaveResponse(this.employeesService.create(this.employees));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IEmployees>>) {
        result.subscribe((res: HttpResponse<IEmployees>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}
