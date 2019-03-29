import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { ISocialContact } from 'app/shared/model/social-contact.model';
import { SocialContactService } from './social-contact.service';
import { IEBCard } from 'app/shared/model/eb-card.model';
import { EBCardService } from 'app/entities/eb-card';

@Component({
    selector: 'jhi-social-contact-update',
    templateUrl: './social-contact-update.component.html'
})
export class SocialContactUpdateComponent implements OnInit {
    socialContact: ISocialContact;
    isSaving: boolean;

    ebcards: IEBCard[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected socialContactService: SocialContactService,
        protected eBCardService: EBCardService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ socialContact }) => {
            this.socialContact = socialContact;
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
        if (this.socialContact.id !== undefined) {
            this.subscribeToSaveResponse(this.socialContactService.update(this.socialContact));
        } else {
            this.subscribeToSaveResponse(this.socialContactService.create(this.socialContact));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<ISocialContact>>) {
        result.subscribe((res: HttpResponse<ISocialContact>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
