import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ICategoryType } from 'app/shared/model/category-type.model';

type EntityResponseType = HttpResponse<ICategoryType>;
type EntityArrayResponseType = HttpResponse<ICategoryType[]>;

@Injectable({ providedIn: 'root' })
export class CategoryTypeService {
    public resourceUrl = SERVER_API_URL + 'api/category-types';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/category-types';

    constructor(protected http: HttpClient) {}

    create(categoryType: ICategoryType): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(categoryType);
        return this.http
            .post<ICategoryType>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(categoryType: ICategoryType): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(categoryType);
        return this.http
            .put<ICategoryType>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<ICategoryType>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<ICategoryType[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<ICategoryType[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    protected convertDateFromClient(categoryType: ICategoryType): ICategoryType {
        const copy: ICategoryType = Object.assign({}, categoryType, {
            createdDate: categoryType.createdDate != null && categoryType.createdDate.isValid() ? categoryType.createdDate.toJSON() : null,
            updatedDate: categoryType.updatedDate != null && categoryType.updatedDate.isValid() ? categoryType.updatedDate.toJSON() : null
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
            res.body.forEach((categoryType: ICategoryType) => {
                categoryType.createdDate = categoryType.createdDate != null ? moment(categoryType.createdDate) : null;
                categoryType.updatedDate = categoryType.updatedDate != null ? moment(categoryType.updatedDate) : null;
            });
        }
        return res;
    }
}
