import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IEBCard } from 'app/shared/model/eb-card.model';
import { EBCardService } from './eb-card.service';
import { ICategory } from 'app/shared/model/category.model';
import { CategoryService } from 'app/entities/category';
import { IUserPropio } from 'app/shared/model/user-propio.model';
import { UserPropioService } from 'app/entities/user-propio';

@Component({
    selector: 'jhi-eb-card-update',
    templateUrl: './eb-card-update.component.html'
})
export class EBCardUpdateComponent implements OnInit {
    eBCard: IEBCard;
    isSaving: boolean;

    categories: ICategory[];

    userpropios: IUserPropio[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected eBCardService: EBCardService,
        protected categoryService: CategoryService,
        protected userPropioService: UserPropioService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ eBCard }) => {
            this.eBCard = eBCard;
        });
        this.categoryService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<ICategory[]>) => mayBeOk.ok),
                map((response: HttpResponse<ICategory[]>) => response.body)
            )
            .subscribe((res: ICategory[]) => (this.categories = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.userPropioService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IUserPropio[]>) => mayBeOk.ok),
                map((response: HttpResponse<IUserPropio[]>) => response.body)
            )
            .subscribe((res: IUserPropio[]) => (this.userpropios = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.eBCard.id !== undefined) {
            this.subscribeToSaveResponse(this.eBCardService.update(this.eBCard));
        } else {
            this.subscribeToSaveResponse(this.eBCardService.create(this.eBCard));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IEBCard>>) {
        result.subscribe((res: HttpResponse<IEBCard>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackCategoryById(index: number, item: ICategory) {
        return item.id;
    }

    trackUserPropioById(index: number, item: IUserPropio) {
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
