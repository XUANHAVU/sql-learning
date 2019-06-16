import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {SERVER_API_URL} from 'app/app.constants';

@Injectable({providedIn: 'root'})
export class TryItService {
    public resourceUrl = SERVER_API_URL + 'api/try-it';

    constructor(protected http: HttpClient) {
    }
}
