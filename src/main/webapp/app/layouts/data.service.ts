import {Injectable} from '@angular/core';
import {BehaviorSubject} from 'rxjs';

@Injectable()
export class DataService {
    private DefaultId = new BehaviorSubject<number>(null);
    private DefaultName = new BehaviorSubject<string>('');
    private DefaultTitle = new BehaviorSubject<string>('');
    private DefaultDescription = new BehaviorSubject<string>('');
    private DefaultTable = new BehaviorSubject<string>('');

    currentId = this.DefaultId.asObservable();
    currentName = this.DefaultName.asObservable();
    currentTitle = this.DefaultTitle.asObservable();
    currentDescription = this.DefaultDescription.asObservable();
    currentTable = this.DefaultTable.asObservable();

    constructor() {
    }

    changeNode(id: number) {
        this.DefaultId.next(id);
    }
}
