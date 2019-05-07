import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IExercises } from 'app/shared/model/exercises.model';

type EntityResponseType = HttpResponse<IExercises>;
type EntityArrayResponseType = HttpResponse<IExercises[]>;

@Injectable({ providedIn: 'root' })
export class ExercisesService {
    public resourceUrl = SERVER_API_URL + 'api/exercises';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/exercises';

    constructor(protected http: HttpClient) {}

    create(exercises: IExercises): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(exercises);
        return this.http
            .post<IExercises>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(exercises: IExercises): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(exercises);
        return this.http
            .put<IExercises>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IExercises>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IExercises[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IExercises[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    protected convertDateFromClient(exercises: IExercises): IExercises {
        const copy: IExercises = Object.assign({}, exercises, {
            createdDate: exercises.createdDate != null && exercises.createdDate.isValid() ? exercises.createdDate.toJSON() : null,
            updatedDate: exercises.updatedDate != null && exercises.updatedDate.isValid() ? exercises.updatedDate.toJSON() : null
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
            res.body.forEach((exercises: IExercises) => {
                exercises.createdDate = exercises.createdDate != null ? moment(exercises.createdDate) : null;
                exercises.updatedDate = exercises.updatedDate != null ? moment(exercises.updatedDate) : null;
            });
        }
        return res;
    }
}
