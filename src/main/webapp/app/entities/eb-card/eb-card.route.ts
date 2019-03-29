import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { EBCard } from 'app/shared/model/eb-card.model';
import { EBCardService } from './eb-card.service';
import { EBCardComponent } from './eb-card.component';
import { EBCardDetailComponent } from './eb-card-detail.component';
import { EBCardUpdateComponent } from './eb-card-update.component';
import { EBCardDeletePopupComponent } from './eb-card-delete-dialog.component';
import { IEBCard } from 'app/shared/model/eb-card.model';

@Injectable({ providedIn: 'root' })
export class EBCardResolve implements Resolve<IEBCard> {
    constructor(private service: EBCardService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IEBCard> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<EBCard>) => response.ok),
                map((eBCard: HttpResponse<EBCard>) => eBCard.body)
            );
        }
        return of(new EBCard());
    }
}

export const eBCardRoute: Routes = [
    {
        path: '',
        component: EBCardComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ebCardApp.eBCard.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: EBCardDetailComponent,
        resolve: {
            eBCard: EBCardResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ebCardApp.eBCard.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: EBCardUpdateComponent,
        resolve: {
            eBCard: EBCardResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ebCardApp.eBCard.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: EBCardUpdateComponent,
        resolve: {
            eBCard: EBCardResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ebCardApp.eBCard.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const eBCardPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: EBCardDeletePopupComponent,
        resolve: {
            eBCard: EBCardResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ebCardApp.eBCard.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
