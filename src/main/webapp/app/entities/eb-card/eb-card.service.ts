import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IEBCard } from 'app/shared/model/eb-card.model';

type EntityResponseType = HttpResponse<IEBCard>;
type EntityArrayResponseType = HttpResponse<IEBCard[]>;

@Injectable({ providedIn: 'root' })
export class EBCardService {
    public resourceUrl = SERVER_API_URL + 'api/eb-cards';

    constructor(protected http: HttpClient) {}

    create(eBCard: IEBCard): Observable<EntityResponseType> {
        return this.http.post<IEBCard>(this.resourceUrl, eBCard, { observe: 'response' });
    }

    update(eBCard: IEBCard): Observable<EntityResponseType> {
        return this.http.put<IEBCard>(this.resourceUrl, eBCard, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IEBCard>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IEBCard[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
