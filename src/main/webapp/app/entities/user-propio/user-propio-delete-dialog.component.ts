import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IUserPropio } from 'app/shared/model/user-propio.model';
import { UserPropioService } from './user-propio.service';

@Component({
    selector: 'jhi-user-propio-delete-dialog',
    templateUrl: './user-propio-delete-dialog.component.html'
})
export class UserPropioDeleteDialogComponent {
    userPropio: IUserPropio;

    constructor(
        protected userPropioService: UserPropioService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.userPropioService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'userPropioListModification',
                content: 'Deleted an userPropio'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-user-propio-delete-popup',
    template: ''
})
export class UserPropioDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ userPropio }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(UserPropioDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.userPropio = userPropio;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/user-propio', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/user-propio', { outlets: { popup: null } }]);
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
