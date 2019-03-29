import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IUserPropio } from 'app/shared/model/user-propio.model';

@Component({
    selector: 'jhi-user-propio-detail',
    templateUrl: './user-propio-detail.component.html'
})
export class UserPropioDetailComponent implements OnInit {
    userPropio: IUserPropio;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ userPropio }) => {
            this.userPropio = userPropio;
        });
    }

    previousState() {
        window.history.back();
    }
}
