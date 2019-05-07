/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { LearnSqlTestModule } from '../../../test.module';
import { TypeContentUpdateComponent } from 'app/entities/type-content/type-content-update.component';
import { TypeContentService } from 'app/entities/type-content/type-content.service';
import { TypeContent } from 'app/shared/model/type-content.model';

describe('Component Tests', () => {
    describe('TypeContent Management Update Component', () => {
        let comp: TypeContentUpdateComponent;
        let fixture: ComponentFixture<TypeContentUpdateComponent>;
        let service: TypeContentService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [LearnSqlTestModule],
                declarations: [TypeContentUpdateComponent]
            })
                .overrideTemplate(TypeContentUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(TypeContentUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TypeContentService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new TypeContent(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.typeContent = entity;
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
                    const entity = new TypeContent();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.typeContent = entity;
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
