import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITypeContent } from 'app/shared/model/type-content.model';

@Component({
    selector: 'jhi-type-content-detail',
    templateUrl: './type-content-detail.component.html'
})
export class TypeContentDetailComponent implements OnInit {
    typeContent: ITypeContent;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ typeContent }) => {
            this.typeContent = typeContent;
        });
    }

    previousState() {
        window.history.back();
    }
}
