import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICategoryType } from 'app/shared/model/category-type.model';

@Component({
    selector: 'jhi-category-type-detail',
    templateUrl: './category-type-detail.component.html'
})
export class CategoryTypeDetailComponent implements OnInit {
    categoryType: ICategoryType;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ categoryType }) => {
            this.categoryType = categoryType;
        });
    }

    previousState() {
        window.history.back();
    }
}
