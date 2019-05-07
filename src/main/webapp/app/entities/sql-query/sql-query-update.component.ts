import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { ISQLQuery } from 'app/shared/model/sql-query.model';
import { SQLQueryService } from './sql-query.service';

@Component({
    selector: 'jhi-sql-query-update',
    templateUrl: './sql-query-update.component.html'
})
export class SQLQueryUpdateComponent implements OnInit {
    sQLQuery: ISQLQuery;
    isSaving: boolean;
    createdDate: string;
    updatedDate: string;

    constructor(protected sQLQueryService: SQLQueryService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ sQLQuery }) => {
            this.sQLQuery = sQLQuery;
            this.createdDate = this.sQLQuery.createdDate != null ? this.sQLQuery.createdDate.format(DATE_TIME_FORMAT) : null;
            this.updatedDate = this.sQLQuery.updatedDate != null ? this.sQLQuery.updatedDate.format(DATE_TIME_FORMAT) : null;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        this.sQLQuery.createdDate = this.createdDate != null ? moment(this.createdDate, DATE_TIME_FORMAT) : null;
        this.sQLQuery.updatedDate = this.updatedDate != null ? moment(this.updatedDate, DATE_TIME_FORMAT) : null;
        if (this.sQLQuery.id !== undefined) {
            this.subscribeToSaveResponse(this.sQLQueryService.update(this.sQLQuery));
        } else {
            this.subscribeToSaveResponse(this.sQLQueryService.create(this.sQLQuery));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<ISQLQuery>>) {
        result.subscribe((res: HttpResponse<ISQLQuery>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}
