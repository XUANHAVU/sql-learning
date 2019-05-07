import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IExercisesAnswer } from 'app/shared/model/exercises-answer.model';

type EntityResponseType = HttpResponse<IExercisesAnswer>;
type EntityArrayResponseType = HttpResponse<IExercisesAnswer[]>;

@Injectable({ providedIn: 'root' })
export class ExercisesAnswerService {
    public resourceUrl = SERVER_API_URL + 'api/exercises-answers';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/exercises-answers';

    constructor(protected http: HttpClient) {}

    create(exercisesAnswer: IExercisesAnswer): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(exercisesAnswer);
        return this.http
            .post<IExercisesAnswer>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(exercisesAnswer: IExercisesAnswer): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(exercisesAnswer);
        return this.http
            .put<IExercisesAnswer>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IExercisesAnswer>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IExercisesAnswer[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IExercisesAnswer[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    protected convertDateFromClient(exercisesAnswer: IExercisesAnswer): IExercisesAnswer {
        const copy: IExercisesAnswer = Object.assign({}, exercisesAnswer, {
            createdDate:
                exercisesAnswer.createdDate != null && exercisesAnswer.createdDate.isValid() ? exercisesAnswer.createdDate.toJSON() : null,
            updatedDate:
                exercisesAnswer.updatedDate != null && exercisesAnswer.updatedDate.isValid() ? exercisesAnswer.updatedDate.toJSON() : null
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
            res.body.forEach((exercisesAnswer: IExercisesAnswer) => {
                exercisesAnswer.createdDate = exercisesAnswer.createdDate != null ? moment(exercisesAnswer.createdDate) : null;
                exercisesAnswer.updatedDate = exercisesAnswer.updatedDate != null ? moment(exercisesAnswer.updatedDate) : null;
            });
        }
        return res;
    }
}
