import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISocialContact } from 'app/shared/model/social-contact.model';
import { SocialContactService } from './social-contact.service';

@Component({
    selector: 'jhi-social-contact-delete-dialog',
    templateUrl: './social-contact-delete-dialog.component.html'
})
export class SocialContactDeleteDialogComponent {
    socialContact: ISocialContact;

    constructor(
        protected socialContactService: SocialContactService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.socialContactService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'socialContactListModification',
                content: 'Deleted an socialContact'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-social-contact-delete-popup',
    template: ''
})
export class SocialContactDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ socialContact }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(SocialContactDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.socialContact = socialContact;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/social-contact', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/social-contact', { outlets: { popup: null } }]);
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
