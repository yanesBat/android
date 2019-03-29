import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { EbCardSharedModule } from 'app/shared';
import {
    UserPropioComponent,
    UserPropioDetailComponent,
    UserPropioUpdateComponent,
    UserPropioDeletePopupComponent,
    UserPropioDeleteDialogComponent,
    userPropioRoute,
    userPropioPopupRoute
} from './';

const ENTITY_STATES = [...userPropioRoute, ...userPropioPopupRoute];

@NgModule({
    imports: [EbCardSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        UserPropioComponent,
        UserPropioDetailComponent,
        UserPropioUpdateComponent,
        UserPropioDeleteDialogComponent,
        UserPropioDeletePopupComponent
    ],
    entryComponents: [UserPropioComponent, UserPropioUpdateComponent, UserPropioDeleteDialogComponent, UserPropioDeletePopupComponent],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class EbCardUserPropioModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
