import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { ICategoryType } from 'app/shared/model/category-type.model';
import { CategoryTypeService } from './category-type.service';

@Component({
    selector: 'jhi-category-type-update',
    templateUrl: './category-type-update.component.html'
})
export class CategoryTypeUpdateComponent implements OnInit {
    categoryType: ICategoryType;
    isSaving: boolean;
    createdDate: string;
    updatedDate: string;

    constructor(protected categoryTypeService: CategoryTypeService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ categoryType }) => {
            this.categoryType = categoryType;
            this.createdDate = this.categoryType.createdDate != null ? this.categoryType.createdDate.format(DATE_TIME_FORMAT) : null;
            this.updatedDate = this.categoryType.updatedDate != null ? this.categoryType.updatedDate.format(DATE_TIME_FORMAT) : null;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        this.categoryType.createdDate = this.createdDate != null ? moment(this.createdDate, DATE_TIME_FORMAT) : null;
        this.categoryType.updatedDate = this.updatedDate != null ? moment(this.updatedDate, DATE_TIME_FORMAT) : null;
        if (this.categoryType.id !== undefined) {
            this.subscribeToSaveResponse(this.categoryTypeService.update(this.categoryType));
        } else {
            this.subscribeToSaveResponse(this.categoryTypeService.create(this.categoryType));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<ICategoryType>>) {
        result.subscribe((res: HttpResponse<ICategoryType>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}
