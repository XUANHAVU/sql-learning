import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IContent } from 'app/shared/model/content.model';

@Component({
    selector: 'jhi-content-detail',
    templateUrl: './content-detail.component.html'
})
export class ContentDetailComponent implements OnInit {
    content: IContent;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ content }) => {
            this.content = content;
        });
    }

    previousState() {
        window.history.back();
    }
}
