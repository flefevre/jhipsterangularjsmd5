import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { Jhipsterangularjsmd5SharedModule } from 'app/shared';
import {
    FilehashComponent,
    FilehashDetailComponent,
    FilehashUpdateComponent,
    FilehashDeletePopupComponent,
    FilehashDeleteDialogComponent,
    filehashRoute,
    filehashPopupRoute
} from './';

const ENTITY_STATES = [...filehashRoute, ...filehashPopupRoute];

@NgModule({
    imports: [Jhipsterangularjsmd5SharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        FilehashComponent,
        FilehashDetailComponent,
        FilehashUpdateComponent,
        FilehashDeleteDialogComponent,
        FilehashDeletePopupComponent
    ],
    entryComponents: [FilehashComponent, FilehashUpdateComponent, FilehashDeleteDialogComponent, FilehashDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class Jhipsterangularjsmd5FilehashModule {}
