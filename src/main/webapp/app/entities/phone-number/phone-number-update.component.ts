import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IPhoneNumber } from 'app/shared/model/phone-number.model';
import { PhoneNumberService } from './phone-number.service';
import { IEBCard } from 'app/shared/model/eb-card.model';
import { EBCardService } from 'app/entities/eb-card';

@Component({
    selector: 'jhi-phone-number-update',
    templateUrl: './phone-number-update.component.html'
})
export class PhoneNumberUpdateComponent implements OnInit {
    phoneNumber: IPhoneNumber;
    isSaving: boolean;

    ebcards: IEBCard[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected phoneNumberService: PhoneNumberService,
        protected eBCardService: EBCardService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ phoneNumber }) => {
            this.phoneNumber = phoneNumber;
        });
        this.eBCardService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IEBCard[]>) => mayBeOk.ok),
                map((response: HttpResponse<IEBCard[]>) => response.body)
            )
            .subscribe((res: IEBCard[]) => (this.ebcards = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.phoneNumber.id !== undefined) {
            this.subscribeToSaveResponse(this.phoneNumberService.update(this.phoneNumber));
        } else {
            this.subscribeToSaveResponse(this.phoneNumberService.create(this.phoneNumber));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IPhoneNumber>>) {
        result.subscribe((res: HttpResponse<IPhoneNumber>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackEBCardById(index: number, item: IEBCard) {
        return item.id;
    }
}
