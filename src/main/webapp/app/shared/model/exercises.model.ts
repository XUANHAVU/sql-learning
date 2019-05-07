import { Moment } from 'moment';
import { IExercisesAnswer } from 'app/shared/model/exercises-answer.model';

export interface IExercises {
    id?: number;
    nameExercises?: string;
    question?: string;
    score?: number;
    timeOnMinutes?: number;
    createdDate?: Moment;
    createdBy?: number;
    updatedDate?: Moment;
    updatedBy?: number;
    categoryId?: number;
    exercisesIDS?: IExercisesAnswer[];
}

export class Exercises implements IExercises {
    constructor(
        public id?: number,
        public nameExercises?: string,
        public question?: string,
        public score?: number,
        public timeOnMinutes?: number,
        public createdDate?: Moment,
        public createdBy?: number,
        public updatedDate?: Moment,
        public updatedBy?: number,
        public categoryId?: number,
        public exercisesIDS?: IExercisesAnswer[]
    ) {}
}
