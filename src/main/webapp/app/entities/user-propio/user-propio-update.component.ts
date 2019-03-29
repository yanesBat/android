import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IUserPropio } from 'app/shared/model/user-propio.model';
import { UserPropioService } from './user-propio.service';
import { IEBCard } from 'app/shared/model/eb-card.model';
import { EBCardService } from 'app/entities/eb-card';
import { IReview } from 'app/shared/model/review.model';
import { ReviewService } from 'app/entities/review';

@Component({
    selector: 'jhi-user-propio-update',
    templateUrl: './user-propio-update.component.html'
})
export class UserPropioUpdateComponent implements OnInit {
    userPropio: IUserPropio;
    isSaving: boolean;

    ebcards: IEBCard[];

    reviews: IReview[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected userPropioService: UserPropioService,
        protected eBCardService: EBCardService,
        protected reviewService: ReviewService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ userPropio }) => {
            this.userPropio = userPropio;
        });
        this.eBCardService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IEBCard[]>) => mayBeOk.ok),
                map((response: HttpResponse<IEBCard[]>) => response.body)
            )
            .subscribe((res: IEBCard[]) => (this.ebcards = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.reviewService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IReview[]>) => mayBeOk.ok),
                map((response: HttpResponse<IReview[]>) => response.body)
            )
            .subscribe((res: IReview[]) => (this.reviews = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.userPropio.id !== undefined) {
            this.subscribeToSaveResponse(this.userPropioService.update(this.userPropio));
        } else {
            this.subscribeToSaveResponse(this.userPropioService.create(this.userPropio));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IUserPropio>>) {
        result.subscribe((res: HttpResponse<IUserPropio>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackReviewById(index: number, item: IReview) {
        return item.id;
    }

    getSelected(selectedVals: Array<any>, option: any) {
        if (selectedVals) {
            for (let i = 0; i < selectedVals.length; i++) {
                if (option.id === selectedVals[i].id) {
                    return selectedVals[i];
                }
            }
        }
        return option;
    }
}
