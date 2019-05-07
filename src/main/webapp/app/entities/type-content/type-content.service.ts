import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ITypeContent } from 'app/shared/model/type-content.model';

type EntityResponseType = HttpResponse<ITypeContent>;
type EntityArrayResponseType = HttpResponse<ITypeContent[]>;

@Injectable({ providedIn: 'root' })
export class TypeContentService {
    public resourceUrl = SERVER_API_URL + 'api/type-contents';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/type-contents';

    constructor(protected http: HttpClient) {}

    create(typeContent: ITypeContent): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(typeContent);
        return this.http
            .post<ITypeContent>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(typeContent: ITypeContent): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(typeContent);
        return this.http
            .put<ITypeContent>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<ITypeContent>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<ITypeContent[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<ITypeContent[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    protected convertDateFromClient(typeContent: ITypeContent): ITypeContent {
        const copy: ITypeContent = Object.assign({}, typeContent, {
            createdDate: typeContent.createdDate != null && typeContent.createdDate.isValid() ? typeContent.createdDate.toJSON() : null,
            updatedDate: typeContent.updatedDate != null && typeContent.updatedDate.isValid() ? typeContent.updatedDate.toJSON() : null
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
            res.body.forEach((typeContent: ITypeContent) => {
                typeContent.createdDate = typeContent.createdDate != null ? moment(typeContent.createdDate) : null;
                typeContent.updatedDate = typeContent.updatedDate != null ? moment(typeContent.updatedDate) : null;
            });
        }
        return res;
    }
}
