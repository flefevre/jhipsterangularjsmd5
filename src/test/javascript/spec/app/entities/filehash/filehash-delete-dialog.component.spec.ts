/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { Jhipsterangularjsmd5TestModule } from '../../../test.module';
import { FilehashDeleteDialogComponent } from 'app/entities/filehash/filehash-delete-dialog.component';
import { FilehashService } from 'app/entities/filehash/filehash.service';

describe('Component Tests', () => {
    describe('Filehash Management Delete Component', () => {
        let comp: FilehashDeleteDialogComponent;
        let fixture: ComponentFixture<FilehashDeleteDialogComponent>;
        let service: FilehashService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [Jhipsterangularjsmd5TestModule],
                declarations: [FilehashDeleteDialogComponent]
            })
                .overrideTemplate(FilehashDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(FilehashDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(FilehashService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it(
                'Should call delete service on confirmDelete',
                inject(
                    [],
                    fakeAsync(() => {
                        // GIVEN
                        spyOn(service, 'delete').and.returnValue(of({}));

                        // WHEN
                        comp.confirmDelete(123);
                        tick();

                        // THEN
                        expect(service.delete).toHaveBeenCalledWith(123);
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });
});
