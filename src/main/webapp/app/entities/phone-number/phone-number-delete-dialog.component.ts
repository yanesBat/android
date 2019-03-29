import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPhoneNumber } from 'app/shared/model/phone-number.model';
import { PhoneNumberService } from './phone-number.service';

@Component({
    selector: 'jhi-phone-number-delete-dialog',
    templateUrl: './phone-number-delete-dialog.component.html'
})
export class PhoneNumberDeleteDialogComponent {
    phoneNumber: IPhoneNumber;

    constructor(
        protected phoneNumberService: PhoneNumberService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.phoneNumberService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'phoneNumberListModification',
                content: 'Deleted an phoneNumber'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-phone-number-delete-popup',
    template: ''
})
export class PhoneNumberDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ phoneNumber }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(PhoneNumberDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.phoneNumber = phoneNumber;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/phone-number', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/phone-number', { outlets: { popup: null } }]);
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
