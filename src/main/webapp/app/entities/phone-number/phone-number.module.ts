import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { EbCardSharedModule } from 'app/shared';
import {
    PhoneNumberComponent,
    PhoneNumberDetailComponent,
    PhoneNumberUpdateComponent,
    PhoneNumberDeletePopupComponent,
    PhoneNumberDeleteDialogComponent,
    phoneNumberRoute,
    phoneNumberPopupRoute
} from './';

const ENTITY_STATES = [...phoneNumberRoute, ...phoneNumberPopupRoute];

@NgModule({
    imports: [EbCardSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        PhoneNumberComponent,
        PhoneNumberDetailComponent,
        PhoneNumberUpdateComponent,
        PhoneNumberDeleteDialogComponent,
        PhoneNumberDeletePopupComponent
    ],
    entryComponents: [PhoneNumberComponent, PhoneNumberUpdateComponent, PhoneNumberDeleteDialogComponent, PhoneNumberDeletePopupComponent],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class EbCardPhoneNumberModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
