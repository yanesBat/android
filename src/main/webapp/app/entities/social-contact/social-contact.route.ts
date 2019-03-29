import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { SocialContact } from 'app/shared/model/social-contact.model';
import { SocialContactService } from './social-contact.service';
import { SocialContactComponent } from './social-contact.component';
import { SocialContactDetailComponent } from './social-contact-detail.component';
import { SocialContactUpdateComponent } from './social-contact-update.component';
import { SocialContactDeletePopupComponent } from './social-contact-delete-dialog.component';
import { ISocialContact } from 'app/shared/model/social-contact.model';

@Injectable({ providedIn: 'root' })
export class SocialContactResolve implements Resolve<ISocialContact> {
    constructor(private service: SocialContactService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ISocialContact> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<SocialContact>) => response.ok),
                map((socialContact: HttpResponse<SocialContact>) => socialContact.body)
            );
        }
        return of(new SocialContact());
    }
}

export const socialContactRoute: Routes = [
    {
        path: '',
        component: SocialContactComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'ebCardApp.socialContact.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: SocialContactDetailComponent,
        resolve: {
            socialContact: SocialContactResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ebCardApp.socialContact.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: SocialContactUpdateComponent,
        resolve: {
            socialContact: SocialContactResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ebCardApp.socialContact.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: SocialContactUpdateComponent,
        resolve: {
            socialContact: SocialContactResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ebCardApp.socialContact.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const socialContactPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: SocialContactDeletePopupComponent,
        resolve: {
            socialContact: SocialContactResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ebCardApp.socialContact.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
