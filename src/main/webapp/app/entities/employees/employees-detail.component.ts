import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IEmployees } from 'app/shared/model/employees.model';

@Component({
    selector: 'jhi-employees-detail',
    templateUrl: './employees-detail.component.html'
})
export class EmployeesDetailComponent implements OnInit {
    employees: IEmployees;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ employees }) => {
            this.employees = employees;
        });
    }

    previousState() {
        window.history.back();
    }
}
