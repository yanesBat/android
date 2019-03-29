import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { EbCardSharedModule } from 'app/shared';
import {
    EBCardComponent,
    EBCardDetailComponent,
    EBCardUpdateComponent,
    EBCardDeletePopupComponent,
    EBCardDeleteDialogComponent,
    eBCardRoute,
    eBCardPopupRoute
} from './';

const ENTITY_STATES = [...eBCardRoute, ...eBCardPopupRoute];

@NgModule({
    imports: [EbCardSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [EBCardComponent, EBCardDetailComponent, EBCardUpdateComponent, EBCardDeleteDialogComponent, EBCardDeletePopupComponent],
    entryComponents: [EBCardComponent, EBCardUpdateComponent, EBCardDeleteDialogComponent, EBCardDeletePopupComponent],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class EbCardEBCardModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
