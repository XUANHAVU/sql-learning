/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { LearnSqlTestModule } from '../../../test.module';
import { TypeContentComponent } from 'app/entities/type-content/type-content.component';
import { TypeContentService } from 'app/entities/type-content/type-content.service';
import { TypeContent } from 'app/shared/model/type-content.model';

describe('Component Tests', () => {
    describe('TypeContent Management Component', () => {
        let comp: TypeContentComponent;
        let fixture: ComponentFixture<TypeContentComponent>;
        let service: TypeContentService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [LearnSqlTestModule],
                declarations: [TypeContentComponent],
                providers: []
            })
                .overrideTemplate(TypeContentComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(TypeContentComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TypeContentService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new TypeContent(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.typeContents[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
