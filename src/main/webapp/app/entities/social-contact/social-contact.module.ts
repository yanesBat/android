import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { EbCardSharedModule } from 'app/shared';
import {
    SocialContactComponent,
    SocialContactDetailComponent,
    SocialContactUpdateComponent,
    SocialContactDeletePopupComponent,
    SocialContactDeleteDialogComponent,
    socialContactRoute,
    socialContactPopupRoute
} from './';

const ENTITY_STATES = [...socialContactRoute, ...socialContactPopupRoute];

@NgModule({
    imports: [EbCardSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        SocialContactComponent,
        SocialContactDetailComponent,
        SocialContactUpdateComponent,
        SocialContactDeleteDialogComponent,
        SocialContactDeletePopupComponent
    ],
    entryComponents: [
        SocialContactComponent,
        SocialContactUpdateComponent,
        SocialContactDeleteDialogComponent,
        SocialContactDeletePopupComponent
    ],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class EbCardSocialContactModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
