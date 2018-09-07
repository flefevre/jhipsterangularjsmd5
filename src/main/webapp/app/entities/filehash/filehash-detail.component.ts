import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IFilehash } from 'app/shared/model/filehash.model';

@Component({
    selector: 'jhi-filehash-detail',
    templateUrl: './filehash-detail.component.html'
})
export class FilehashDetailComponent implements OnInit {
    filehash: IFilehash;

    constructor(private dataUtils: JhiDataUtils, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ filehash }) => {
            this.filehash = filehash;
        });
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }
    previousState() {
        window.history.back();
    }
}
