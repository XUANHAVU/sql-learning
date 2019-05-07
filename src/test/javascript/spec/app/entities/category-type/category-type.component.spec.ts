/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { LearnSqlTestModule } from '../../../test.module';
import { CategoryTypeComponent } from 'app/entities/category-type/category-type.component';
import { CategoryTypeService } from 'app/entities/category-type/category-type.service';
import { CategoryType } from 'app/shared/model/category-type.model';

describe('Component Tests', () => {
    describe('CategoryType Management Component', () => {
        let comp: CategoryTypeComponent;
        let fixture: ComponentFixture<CategoryTypeComponent>;
        let service: CategoryTypeService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [LearnSqlTestModule],
                declarations: [CategoryTypeComponent],
                providers: []
            })
                .overrideTemplate(CategoryTypeComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(CategoryTypeComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CategoryTypeService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new CategoryType(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.categoryTypes[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
