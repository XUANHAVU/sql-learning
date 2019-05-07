import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IEmployees } from 'app/shared/model/employees.model';

type EntityResponseType = HttpResponse<IEmployees>;
type EntityArrayResponseType = HttpResponse<IEmployees[]>;

@Injectable({ providedIn: 'root' })
export class EmployeesService {
    public resourceUrl = SERVER_API_URL + 'api/employees';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/employees';

    constructor(protected http: HttpClient) {}

    create(employees: IEmployees): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(employees);
        return this.http
            .post<IEmployees>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(employees: IEmployees): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(employees);
        return this.http
            .put<IEmployees>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IEmployees>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IEmployees[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IEmployees[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    protected convertDateFromClient(employees: IEmployees): IEmployees {
        const copy: IEmployees = Object.assign({}, employees, {
            birthDate: employees.birthDate != null && employees.birthDate.isValid() ? employees.birthDate.toJSON() : null
        });
        return copy;
    }

    protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
        if (res.body) {
            res.body.birthDate = res.body.birthDate != null ? moment(res.body.birthDate) : null;
        }
        return res;
    }

    protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        if (res.body) {
            res.body.forEach((employees: IEmployees) => {
                employees.birthDate = employees.birthDate != null ? moment(employees.birthDate) : null;
            });
        }
        return res;
    }
}
