/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { LearnSqlTestModule } from '../../../test.module';
import { CategoryTypeDetailComponent } from 'app/entities/category-type/category-type-detail.component';
import { CategoryType } from 'app/shared/model/category-type.model';

describe('Component Tests', () => {
    describe('CategoryType Management Detail Component', () => {
        let comp: CategoryTypeDetailComponent;
        let fixture: ComponentFixture<CategoryTypeDetailComponent>;
        const route = ({ data: of({ categoryType: new CategoryType(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [LearnSqlTestModule],
                declarations: [CategoryTypeDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(CategoryTypeDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(CategoryTypeDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.categoryType).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
