import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { IShipper } from 'app/shared/model/shipper.model';
import { ShipperService } from './shipper.service';

@Component({
    selector: 'jhi-shipper-update',
    templateUrl: './shipper-update.component.html'
})
export class ShipperUpdateComponent implements OnInit {
    shipper: IShipper;
    isSaving: boolean;

    constructor(protected shipperService: ShipperService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ shipper }) => {
            this.shipper = shipper;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.shipper.id !== undefined) {
            this.subscribeToSaveResponse(this.shipperService.update(this.shipper));
        } else {
            this.subscribeToSaveResponse(this.shipperService.create(this.shipper));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IShipper>>) {
        result.subscribe((res: HttpResponse<IShipper>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}
