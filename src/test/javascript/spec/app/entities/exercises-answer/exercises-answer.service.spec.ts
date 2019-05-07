/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { ExercisesAnswerService } from 'app/entities/exercises-answer/exercises-answer.service';
import { IExercisesAnswer, ExercisesAnswer } from 'app/shared/model/exercises-answer.model';

describe('Service Tests', () => {
    describe('ExercisesAnswer Service', () => {
        let injector: TestBed;
        let service: ExercisesAnswerService;
        let httpMock: HttpTestingController;
        let elemDefault: IExercisesAnswer;
        let currentDate: moment.Moment;
        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HttpClientTestingModule]
            });
            injector = getTestBed();
            service = injector.get(ExercisesAnswerService);
            httpMock = injector.get(HttpTestingController);
            currentDate = moment();

            elemDefault = new ExercisesAnswer(0, 'AAAAAAA', false, currentDate, 0, currentDate, 0);
        });

        describe('Service methods', async () => {
            it('should find an element', async () => {
                const returnedFromService = Object.assign(
                    {
                        createdDate: currentDate.format(DATE_TIME_FORMAT),
                        updatedDate: currentDate.format(DATE_TIME_FORMAT)
                    },
                    elemDefault
                );
                service
                    .find(123)
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: elemDefault }));

                const req = httpMock.expectOne({ method: 'GET' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should create a ExercisesAnswer', async () => {
                const returnedFromService = Object.assign(
                    {
                        id: 0,
                        createdDate: currentDate.format(DATE_TIME_FORMAT),
                        updatedDate: currentDate.format(DATE_TIME_FORMAT)
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        createdDate: currentDate,
                        updatedDate: currentDate
                    },
                    returnedFromService
                );
                service
                    .create(new ExercisesAnswer(null))
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'POST' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should update a ExercisesAnswer', async () => {
                const returnedFromService = Object.assign(
                    {
                        result: 'BBBBBB',
                        isCorrect: true,
                        createdDate: currentDate.format(DATE_TIME_FORMAT),
                        createdBy: 1,
                        updatedDate: currentDate.format(DATE_TIME_FORMAT),
                        updatedBy: 1
                    },
                    elemDefault
                );

                const expected = Object.assign(
                    {
                        createdDate: currentDate,
                        updatedDate: currentDate
                    },
                    returnedFromService
                );
                service
                    .update(expected)
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'PUT' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should return a list of ExercisesAnswer', async () => {
                const returnedFromService = Object.assign(
                    {
                        result: 'BBBBBB',
                        isCorrect: true,
                        createdDate: currentDate.format(DATE_TIME_FORMAT),
                        createdBy: 1,
                        updatedDate: currentDate.format(DATE_TIME_FORMAT),
                        updatedBy: 1
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        createdDate: currentDate,
                        updatedDate: currentDate
                    },
                    returnedFromService
                );
                service
                    .query(expected)
                    .pipe(
                        take(1),
                        map(resp => resp.body)
                    )
                    .subscribe(body => expect(body).toContainEqual(expected));
                const req = httpMock.expectOne({ method: 'GET' });
                req.flush(JSON.stringify([returnedFromService]));
                httpMock.verify();
            });

            it('should delete a ExercisesAnswer', async () => {
                const rxPromise = service.delete(123).subscribe(resp => expect(resp.ok));

                const req = httpMock.expectOne({ method: 'DELETE' });
                req.flush({ status: 200 });
            });
        });

        afterEach(() => {
            httpMock.verify();
        });
    });
});
