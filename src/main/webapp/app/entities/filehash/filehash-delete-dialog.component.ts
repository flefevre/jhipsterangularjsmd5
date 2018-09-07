import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IFilehash } from 'app/shared/model/filehash.model';
import { FilehashService } from './filehash.service';

@Component({
    selector: 'jhi-filehash-delete-dialog',
    templateUrl: './filehash-delete-dialog.component.html'
})
export class FilehashDeleteDialogComponent {
    filehash: IFilehash;

    constructor(private filehashService: FilehashService, public activeModal: NgbActiveModal, private eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.filehashService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'filehashListModification',
                content: 'Deleted an filehash'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-filehash-delete-popup',
    template: ''
})
export class FilehashDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ filehash }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(FilehashDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.filehash = filehash;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    }
                );
            }, 0);
        });
    }

    ngOnDestroy() {
        this.ngbModalRef = null;
    }
}
