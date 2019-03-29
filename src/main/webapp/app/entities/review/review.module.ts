import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { EbCardSharedModule } from 'app/shared';
import {
    ReviewComponent,
    ReviewDetailComponent,
    ReviewUpdateComponent,
    ReviewDeletePopupComponent,
    ReviewDeleteDialogComponent,
    reviewRoute,
    reviewPopupRoute
} from './';

const ENTITY_STATES = [...reviewRoute, ...reviewPopupRoute];

@NgModule({
    imports: [EbCardSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [ReviewComponent, ReviewDetailComponent, ReviewUpdateComponent, ReviewDeleteDialogComponent, ReviewDeletePopupComponent],
    entryComponents: [ReviewComponent, ReviewUpdateComponent, ReviewDeleteDialogComponent, ReviewDeletePopupComponent],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class EbCardReviewModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
