import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IFilehash } from 'app/shared/model/filehash.model';

type EntityResponseType = HttpResponse<IFilehash>;
type EntityArrayResponseType = HttpResponse<IFilehash[]>;

@Injectable({ providedIn: 'root' })
export class FilehashService {
    private resourceUrl = SERVER_API_URL + 'api/filehashes';

    constructor(private http: HttpClient) {}

    create(filehash: IFilehash): Observable<EntityResponseType> {
        return this.http.post<IFilehash>(this.resourceUrl, filehash, { observe: 'response' });
    }

    update(filehash: IFilehash): Observable<EntityResponseType> {
        return this.http.put<IFilehash>(this.resourceUrl, filehash, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IFilehash>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IFilehash[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
