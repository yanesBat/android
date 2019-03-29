import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISocialContact } from 'app/shared/model/social-contact.model';

@Component({
    selector: 'jhi-social-contact-detail',
    templateUrl: './social-contact-detail.component.html'
})
export class SocialContactDetailComponent implements OnInit {
    socialContact: ISocialContact;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ socialContact }) => {
            this.socialContact = socialContact;
        });
    }

    previousState() {
        window.history.back();
    }
}
