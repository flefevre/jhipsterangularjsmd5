/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { Jhipsterangularjsmd5TestModule } from '../../../test.module';
import { FilehashUpdateComponent } from 'app/entities/filehash/filehash-update.component';
import { FilehashService } from 'app/entities/filehash/filehash.service';
import { Filehash } from 'app/shared/model/filehash.model';

describe('Component Tests', () => {
    describe('Filehash Management Update Component', () => {
        let comp: FilehashUpdateComponent;
        let fixture: ComponentFixture<FilehashUpdateComponent>;
        let service: FilehashService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [Jhipsterangularjsmd5TestModule],
                declarations: [FilehashUpdateComponent]
            })
                .overrideTemplate(FilehashUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(FilehashUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(FilehashService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Filehash(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.filehash = entity;
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
                    const entity = new Filehash();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.filehash = entity;
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
