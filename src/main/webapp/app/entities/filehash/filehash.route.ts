import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Filehash } from 'app/shared/model/filehash.model';
import { FilehashService } from './filehash.service';
import { FilehashComponent } from './filehash.component';
import { FilehashDetailComponent } from './filehash-detail.component';
import { FilehashUpdateComponent } from './filehash-update.component';
import { FilehashDeletePopupComponent } from './filehash-delete-dialog.component';
import { IFilehash } from 'app/shared/model/filehash.model';

@Injectable({ providedIn: 'root' })
export class FilehashResolve implements Resolve<IFilehash> {
    constructor(private service: FilehashService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((filehash: HttpResponse<Filehash>) => filehash.body));
        }
        return of(new Filehash());
    }
}

export const filehashRoute: Routes = [
    {
        path: 'filehash',
        component: FilehashComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhipsterangularjsmd5App.filehash.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'filehash/:id/view',
        component: FilehashDetailComponent,
        resolve: {
            filehash: FilehashResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhipsterangularjsmd5App.filehash.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'filehash/new',
        component: FilehashUpdateComponent,
        resolve: {
            filehash: FilehashResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhipsterangularjsmd5App.filehash.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'filehash/:id/edit',
        component: FilehashUpdateComponent,
        resolve: {
            filehash: FilehashResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhipsterangularjsmd5App.filehash.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const filehashPopupRoute: Routes = [
    {
        path: 'filehash/:id/delete',
        component: FilehashDeletePopupComponent,
        resolve: {
            filehash: FilehashResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhipsterangularjsmd5App.filehash.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
