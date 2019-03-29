import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ISocialContact } from 'app/shared/model/social-contact.model';

type EntityResponseType = HttpResponse<ISocialContact>;
type EntityArrayResponseType = HttpResponse<ISocialContact[]>;

@Injectable({ providedIn: 'root' })
export class SocialContactService {
    public resourceUrl = SERVER_API_URL + 'api/social-contacts';

    constructor(protected http: HttpClient) {}

    create(socialContact: ISocialContact): Observable<EntityResponseType> {
        return this.http.post<ISocialContact>(this.resourceUrl, socialContact, { observe: 'response' });
    }

    update(socialContact: ISocialContact): Observable<EntityResponseType> {
        return this.http.put<ISocialContact>(this.resourceUrl, socialContact, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<ISocialContact>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<ISocialContact[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
