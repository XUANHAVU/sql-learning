import { Moment } from 'moment';

export interface IExercisesAnswer {
    id?: number;
    result?: string;
    isCorrect?: boolean;
    createdDate?: Moment;
    createdBy?: number;
    updatedDate?: Moment;
    updatedBy?: number;
    exercisesId?: number;
}

export class ExercisesAnswer implements IExercisesAnswer {
    constructor(
        public id?: number,
        public result?: string,
        public isCorrect?: boolean,
        public createdDate?: Moment,
        public createdBy?: number,
        public updatedDate?: Moment,
        public updatedBy?: number,
        public exercisesId?: number
    ) {
        this.isCorrect = this.isCorrect || false;
    }
}
