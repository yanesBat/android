import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { UserPropio } from 'app/shared/model/user-propio.model';
import { UserPropioService } from './user-propio.service';
import { UserPropioComponent } from './user-propio.component';
import { UserPropioDetailComponent } from './user-propio-detail.component';
import { UserPropioUpdateComponent } from './user-propio-update.component';
import { UserPropioDeletePopupComponent } from './user-propio-delete-dialog.component';
import { IUserPropio } from 'app/shared/model/user-propio.model';

@Injectable({ providedIn: 'root' })
export class UserPropioResolve implements Resolve<IUserPropio> {
    constructor(private service: UserPropioService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IUserPropio> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<UserPropio>) => response.ok),
                map((userPropio: HttpResponse<UserPropio>) => userPropio.body)
            );
        }
        return of(new UserPropio());
    }
}

export const userPropioRoute: Routes = [
    {
        path: '',
        component: UserPropioComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'ebCardApp.userPropio.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: UserPropioDetailComponent,
        resolve: {
            userPropio: UserPropioResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ebCardApp.userPropio.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: UserPropioUpdateComponent,
        resolve: {
            userPropio: UserPropioResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ebCardApp.userPropio.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: UserPropioUpdateComponent,
        resolve: {
            userPropio: UserPropioResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ebCardApp.userPropio.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const userPropioPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: UserPropioDeletePopupComponent,
        resolve: {
            userPropio: UserPropioResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ebCardApp.userPropio.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
