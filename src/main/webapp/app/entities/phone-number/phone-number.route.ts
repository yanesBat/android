import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { PhoneNumber } from 'app/shared/model/phone-number.model';
import { PhoneNumberService } from './phone-number.service';
import { PhoneNumberComponent } from './phone-number.component';
import { PhoneNumberDetailComponent } from './phone-number-detail.component';
import { PhoneNumberUpdateComponent } from './phone-number-update.component';
import { PhoneNumberDeletePopupComponent } from './phone-number-delete-dialog.component';
import { IPhoneNumber } from 'app/shared/model/phone-number.model';

@Injectable({ providedIn: 'root' })
export class PhoneNumberResolve implements Resolve<IPhoneNumber> {
    constructor(private service: PhoneNumberService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IPhoneNumber> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<PhoneNumber>) => response.ok),
                map((phoneNumber: HttpResponse<PhoneNumber>) => phoneNumber.body)
            );
        }
        return of(new PhoneNumber());
    }
}

export const phoneNumberRoute: Routes = [
    {
        path: '',
        component: PhoneNumberComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'ebCardApp.phoneNumber.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: PhoneNumberDetailComponent,
        resolve: {
            phoneNumber: PhoneNumberResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ebCardApp.phoneNumber.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: PhoneNumberUpdateComponent,
        resolve: {
            phoneNumber: PhoneNumberResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ebCardApp.phoneNumber.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: PhoneNumberUpdateComponent,
        resolve: {
            phoneNumber: PhoneNumberResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ebCardApp.phoneNumber.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const phoneNumberPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: PhoneNumberDeletePopupComponent,
        resolve: {
            phoneNumber: PhoneNumberResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ebCardApp.phoneNumber.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
