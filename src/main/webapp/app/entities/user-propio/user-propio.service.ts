import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IUserPropio } from 'app/shared/model/user-propio.model';

type EntityResponseType = HttpResponse<IUserPropio>;
type EntityArrayResponseType = HttpResponse<IUserPropio[]>;

@Injectable({ providedIn: 'root' })
export class UserPropioService {
    public resourceUrl = SERVER_API_URL + 'api/user-propios';

    constructor(protected http: HttpClient) {}

    create(userPropio: IUserPropio): Observable<EntityResponseType> {
        return this.http.post<IUserPropio>(this.resourceUrl, userPropio, { observe: 'response' });
    }

    update(userPropio: IUserPropio): Observable<EntityResponseType> {
        return this.http.put<IUserPropio>(this.resourceUrl, userPropio, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IUserPropio>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IUserPropio[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
