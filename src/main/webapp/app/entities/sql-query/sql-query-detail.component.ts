import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISQLQuery } from 'app/shared/model/sql-query.model';

@Component({
    selector: 'jhi-sql-query-detail',
    templateUrl: './sql-query-detail.component.html'
})
export class SQLQueryDetailComponent implements OnInit {
    sQLQuery: ISQLQuery;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ sQLQuery }) => {
            this.sQLQuery = sQLQuery;
        });
    }

    previousState() {
        window.history.back();
    }
}
