import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPhoneNumber } from 'app/shared/model/phone-number.model';

@Component({
    selector: 'jhi-phone-number-detail',
    templateUrl: './phone-number-detail.component.html'
})
export class PhoneNumberDetailComponent implements OnInit {
    phoneNumber: IPhoneNumber;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ phoneNumber }) => {
            this.phoneNumber = phoneNumber;
        });
    }

    previousState() {
        window.history.back();
    }
}
