import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiDataUtils } from 'ng-jhipster';

import { IFilehash } from 'app/shared/model/filehash.model';
import { FilehashService } from './filehash.service';

import { Md5 } from 'ts-md5/dist/md5';

@Component({
    selector: 'jhi-filehash-update',
    templateUrl: './filehash-update.component.html'
})
export class FilehashUpdateComponent implements OnInit {
    private _filehash: IFilehash;
    isSaving: boolean;

    constructor(private dataUtils: JhiDataUtils, private filehashService: FilehashService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ filehash }) => {
            this.filehash = filehash;
        });
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        const toto = Md5.hashAsciiStr('blah blah blah', true);
        return this.dataUtils.openFile(contentType, field);
    }

    setFileData(event, entity, field, isImage) {
        this.dataUtils.setFileData(event, entity, field, isImage);
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.filehash.id !== undefined) {
            this.subscribeToSaveResponse(this.filehashService.update(this.filehash));
        } else {
            console.log('azertyuiop' + this.filehash.contentOfFile);
            const toto = '' + Md5.hashStr('toto' + this.filehash.contentOfFile);
            this.filehash.hashOfFile = '' + toto;
            this.subscribeToSaveResponse(this.filehashService.create(this.filehash));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IFilehash>>) {
        result.subscribe((res: HttpResponse<IFilehash>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
    get filehash() {
        return this._filehash;
    }

    set filehash(filehash: IFilehash) {
        this._filehash = filehash;
        const toto = Md5.hashAsciiStr('blah blah blah', false);
        console.log(toto);
    }
}
