/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { LearnSqlTestModule } from '../../../test.module';
import { ShipperComponent } from 'app/entities/shipper/shipper.component';
import { ShipperService } from 'app/entities/shipper/shipper.service';
import { Shipper } from 'app/shared/model/shipper.model';

describe('Component Tests', () => {
    describe('Shipper Management Component', () => {
        let comp: ShipperComponent;
        let fixture: ComponentFixture<ShipperComponent>;
        let service: ShipperService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [LearnSqlTestModule],
                declarations: [ShipperComponent],
                providers: []
            })
                .overrideTemplate(ShipperComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ShipperComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ShipperService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new Shipper(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.shippers[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
