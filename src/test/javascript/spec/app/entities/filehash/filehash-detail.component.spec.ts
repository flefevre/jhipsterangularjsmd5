/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { Jhipsterangularjsmd5TestModule } from '../../../test.module';
import { FilehashDetailComponent } from 'app/entities/filehash/filehash-detail.component';
import { Filehash } from 'app/shared/model/filehash.model';

describe('Component Tests', () => {
    describe('Filehash Management Detail Component', () => {
        let comp: FilehashDetailComponent;
        let fixture: ComponentFixture<FilehashDetailComponent>;
        const route = ({ data: of({ filehash: new Filehash(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [Jhipsterangularjsmd5TestModule],
                declarations: [FilehashDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(FilehashDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(FilehashDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.filehash).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
