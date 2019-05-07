import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IContent } from 'app/shared/model/content.model';

type EntityResponseType = HttpResponse<IContent>;
type EntityArrayResponseType = HttpResponse<IContent[]>;

@Injectable({ providedIn: 'root' })
export class ContentService {
    public resourceUrl = SERVER_API_URL + 'api/contents';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/contents';

    constructor(protected http: HttpClient) {}

    create(content: IContent): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(content);
        return this.http
            .post<IContent>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(content: IContent): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(content);
        return this.http
            .put<IContent>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IContent>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IContent[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IContent[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    protected convertDateFromClient(content: IContent): IContent {
        const copy: IContent = Object.assign({}, content, {
            createdDate: content.createdDate != null && content.createdDate.isValid() ? content.createdDate.toJSON() : null,
            updatedDate: content.updatedDate != null && content.updatedDate.isValid() ? content.updatedDate.toJSON() : null
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
            res.body.forEach((content: IContent) => {
                content.createdDate = content.createdDate != null ? moment(content.createdDate) : null;
                content.updatedDate = content.updatedDate != null ? moment(content.updatedDate) : null;
            });
        }
        return res;
    }
}
