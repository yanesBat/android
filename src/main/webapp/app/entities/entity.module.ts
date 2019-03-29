import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
    imports: [
        RouterModule.forChild([
            {
                path: 'eb-card',
                loadChildren: './eb-card/eb-card.module#EbCardEBCardModule'
            },
            {
                path: 'user-propio',
                loadChildren: './user-propio/user-propio.module#EbCardUserPropioModule'
            },
            {
                path: 'category',
                loadChildren: './category/category.module#EbCardCategoryModule'
            },
            {
                path: 'address',
                loadChildren: './address/address.module#EbCardAddressModule'
            },
            {
                path: 'phone-number',
                loadChildren: './phone-number/phone-number.module#EbCardPhoneNumberModule'
            },
            {
                path: 'social-contact',
                loadChildren: './social-contact/social-contact.module#EbCardSocialContactModule'
            },
            {
                path: 'review',
                loadChildren: './review/review.module#EbCardReviewModule'
            }
            /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
        ])
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class EbCardEntityModule {}
