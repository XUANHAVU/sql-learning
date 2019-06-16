export interface ITryIt {
    query?: string;
}

export class TryIt implements ITryIt {
    constructor(
        public query?: string
    ) {
    }
}
