/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { LearnSqlTestModule } from '../../../test.module';
import { TypeContentDetailComponent } from 'app/entities/type-content/type-content-detail.component';
import { TypeContent } from 'app/shared/model/type-content.model';

describe('Component Tests', () => {
    describe('TypeContent Management Detail Component', () => {
        let comp: TypeContentDetailComponent;
        let fixture: ComponentFixture<TypeContentDetailComponent>;
        const route = ({ data: of({ typeContent: new TypeContent(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [LearnSqlTestModule],
                declarations: [TypeContentDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(TypeContentDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(TypeContentDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.typeContent).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
