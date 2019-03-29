import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IEBCard } from 'app/shared/model/eb-card.model';

@Component({
    selector: 'jhi-eb-card-detail',
    templateUrl: './eb-card-detail.component.html'
})
export class EBCardDetailComponent implements OnInit {
    eBCard: IEBCard;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ eBCard }) => {
            this.eBCard = eBCard;
        });
    }

    previousState() {
        window.history.back();
    }
}
