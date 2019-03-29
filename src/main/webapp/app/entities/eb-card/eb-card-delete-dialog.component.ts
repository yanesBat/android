import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IEBCard } from 'app/shared/model/eb-card.model';
import { EBCardService } from './eb-card.service';

@Component({
    selector: 'jhi-eb-card-delete-dialog',
    templateUrl: './eb-card-delete-dialog.component.html'
})
export class EBCardDeleteDialogComponent {
    eBCard: IEBCard;

    constructor(protected eBCardService: EBCardService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.eBCardService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'eBCardListModification',
                content: 'Deleted an eBCard'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-eb-card-delete-popup',
    template: ''
})
export class EBCardDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ eBCard }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(EBCardDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.eBCard = eBCard;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/eb-card', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/eb-card', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    }
                );
            }, 0);
        });
    }

    ngOnDestroy() {
        this.ngbModalRef = null;
    }
}
