import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ISQLQuery } from 'app/shared/model/sql-query.model';

type EntityResponseType = HttpResponse<ISQLQuery>;
type EntityArrayResponseType = HttpResponse<ISQLQuery[]>;

@Injectable({ providedIn: 'root' })
export class SQLQueryService {
    public resourceUrl = SERVER_API_URL + 'api/sql-queries';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/sql-queries';

    constructor(protected http: HttpClient) {}

    create(sQLQuery: ISQLQuery): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(sQLQuery);
        return this.http
            .post<ISQLQuery>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(sQLQuery: ISQLQuery): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(sQLQuery);
        return this.http
            .put<ISQLQuery>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<ISQLQuery>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<ISQLQuery[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<ISQLQuery[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    protected convertDateFromClient(sQLQuery: ISQLQuery): ISQLQuery {
        const copy: ISQLQuery = Object.assign({}, sQLQuery, {
            createdDate: sQLQuery.createdDate != null && sQLQuery.createdDate.isValid() ? sQLQuery.createdDate.toJSON() : null,
            updatedDate: sQLQuery.updatedDate != null && sQLQuery.updatedDate.isValid() ? sQLQuery.updatedDate.toJSON() : null
        });
        return copy;
    }

    protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
        if (res.body) {
            res.body.createdDate = res.body.createdDate != null ? moment(res.body.createdDate) : null;
            res.body.updatedDate = res.body.updatedDate != null ? moment(res.body.updatedDate) : null;
        }
        return res;
    }

    protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        if (res.body) {
            res.body.forEach((sQLQuery: ISQLQuery) => {
                sQLQuery.createdDate = sQLQuery.createdDate != null ? moment(sQLQuery.createdDate) : null;
                sQLQuery.updatedDate = sQLQuery.updatedDate != null ? moment(sQLQuery.updatedDate) : null;
            });
        }
        return res;
    }
}
