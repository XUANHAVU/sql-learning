import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';
import { ICategory } from 'app/shared/model/category.model';
import { CategoryService } from './category.service';
import { ICategoryType } from 'app/shared/model/category-type.model';
import { CategoryTypeService } from 'app/entities/category-type';

@Component({
    selector: 'jhi-category-update',
    templateUrl: './category-update.component.html'
})
export class CategoryUpdateComponent implements OnInit {
    category: ICategory;
    isSaving: boolean;

    categorytypes: ICategoryType[];
    createdDate: string;
    updatedDate: string;

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected categoryService: CategoryService,
        protected categoryTypeService: CategoryTypeService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ category }) => {
            this.category = category;
            this.createdDate = this.category.createdDate != null ? this.category.createdDate.format(DATE_TIME_FORMAT) : null;
            this.updatedDate = this.category.updatedDate != null ? this.category.updatedDate.format(DATE_TIME_FORMAT) : null;
        });
        this.categoryTypeService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<ICategoryType[]>) => mayBeOk.ok),
                map((response: HttpResponse<ICategoryType[]>) => response.body)
            )
            .subscribe((res: ICategoryType[]) => (this.categorytypes = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        this.category.createdDate = this.createdDate != null ? moment(this.createdDate, DATE_TIME_FORMAT) : null;
        this.category.updatedDate = this.updatedDate != null ? moment(this.updatedDate, DATE_TIME_FORMAT) : null;
        if (this.category.id !== undefined) {
            this.subscribeToSaveResponse(this.categoryService.update(this.category));
        } else {
            this.subscribeToSaveResponse(this.categoryService.create(this.category));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<ICategory>>) {
        result.subscribe((res: HttpResponse<ICategory>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackCategoryTypeById(index: number, item: ICategoryType) {
        return item.id;
    }
}
