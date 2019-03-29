import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IReview } from 'app/shared/model/review.model';
import { ReviewService } from './review.service';
import { IUserPropio } from 'app/shared/model/user-propio.model';
import { UserPropioService } from 'app/entities/user-propio';
import { IEBCard } from 'app/shared/model/eb-card.model';
import { EBCardService } from 'app/entities/eb-card';

@Component({
    selector: 'jhi-review-update',
    templateUrl: './review-update.component.html'
})
export class ReviewUpdateComponent implements OnInit {
    review: IReview;
    isSaving: boolean;

    userpropios: IUserPropio[];

    ebcards: IEBCard[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected reviewService: ReviewService,
        protected userPropioService: UserPropioService,
        protected eBCardService: EBCardService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ review }) => {
            this.review = review;
        });
        this.userPropioService
            .query({ filter: 'review-is-null' })
            .pipe(
                filter((mayBeOk: HttpResponse<IUserPropio[]>) => mayBeOk.ok),
                map((response: HttpResponse<IUserPropio[]>) => response.body)
            )
            .subscribe(
                (res: IUserPropio[]) => {
                    if (!this.review.userPropioId) {
                        this.userpropios = res;
                    } else {
                        this.userPropioService
                            .find(this.review.userPropioId)
                            .pipe(
                                filter((subResMayBeOk: HttpResponse<IUserPropio>) => subResMayBeOk.ok),
                                map((subResponse: HttpResponse<IUserPropio>) => subResponse.body)
                            )
                            .subscribe(
                                (subRes: IUserPropio) => (this.userpropios = [subRes].concat(res)),
                                (subRes: HttpErrorResponse) => this.onError(subRes.message)
                            );
                    }
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
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
        if (this.review.id !== undefined) {
            this.subscribeToSaveResponse(this.reviewService.update(this.review));
        } else {
            this.subscribeToSaveResponse(this.reviewService.create(this.review));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IReview>>) {
        result.subscribe((res: HttpResponse<IReview>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackUserPropioById(index: number, item: IUserPropio) {
        return item.id;
    }

    trackEBCardById(index: number, item: IEBCard) {
        return item.id;
    }
}
