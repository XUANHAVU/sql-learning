import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';
import { IContent } from 'app/shared/model/content.model';
import { ContentService } from './content.service';
import { ICategory } from 'app/shared/model/category.model';
import { CategoryService } from 'app/entities/category';
import { ITypeContent } from 'app/shared/model/type-content.model';
import { TypeContentService } from 'app/entities/type-content';

@Component({
    selector: 'jhi-content-update',
    templateUrl: './content-update.component.html'
})
export class ContentUpdateComponent implements OnInit {
    content: IContent;
    isSaving: boolean;

    categories: ICategory[];

    typecontents: ITypeContent[];
    createdDate: string;
    updatedDate: string;

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected contentService: ContentService,
        protected categoryService: CategoryService,
        protected typeContentService: TypeContentService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ content }) => {
            this.content = content;
            this.createdDate = this.content.createdDate != null ? this.content.createdDate.format(DATE_TIME_FORMAT) : null;
            this.updatedDate = this.content.updatedDate != null ? this.content.updatedDate.format(DATE_TIME_FORMAT) : null;
        });
        this.categoryService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<ICategory[]>) => mayBeOk.ok),
                map((response: HttpResponse<ICategory[]>) => response.body)
            )
            .subscribe((res: ICategory[]) => (this.categories = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.typeContentService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<ITypeContent[]>) => mayBeOk.ok),
                map((response: HttpResponse<ITypeContent[]>) => response.body)
            )
            .subscribe((res: ITypeContent[]) => (this.typecontents = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        this.content.createdDate = this.createdDate != null ? moment(this.createdDate, DATE_TIME_FORMAT) : null;
        this.content.updatedDate = this.updatedDate != null ? moment(this.updatedDate, DATE_TIME_FORMAT) : null;
        if (this.content.id !== undefined) {
            this.subscribeToSaveResponse(this.contentService.update(this.content));
        } else {
            this.subscribeToSaveResponse(this.contentService.create(this.content));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IContent>>) {
        result.subscribe((res: HttpResponse<IContent>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackCategoryById(index: number, item: ICategory) {
        return item.id;
    }

    trackTypeContentById(index: number, item: ITypeContent) {
        return item.id;
    }
}
