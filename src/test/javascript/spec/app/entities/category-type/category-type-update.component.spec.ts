/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { LearnSqlTestModule } from '../../../test.module';
import { CategoryTypeUpdateComponent } from 'app/entities/category-type/category-type-update.component';
import { CategoryTypeService } from 'app/entities/category-type/category-type.service';
import { CategoryType } from 'app/shared/model/category-type.model';

describe('Component Tests', () => {
    describe('CategoryType Management Update Component', () => {
        let comp: CategoryTypeUpdateComponent;
        let fixture: ComponentFixture<CategoryTypeUpdateComponent>;
        let service: CategoryTypeService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [LearnSqlTestModule],
                declarations: [CategoryTypeUpdateComponent]
            })
                .overrideTemplate(CategoryTypeUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(CategoryTypeUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CategoryTypeService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new CategoryType(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.categoryType = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.update).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );

            it(
                'Should call create service on save for new entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new CategoryType();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.categoryType = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.create).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );
        });
    });
});
