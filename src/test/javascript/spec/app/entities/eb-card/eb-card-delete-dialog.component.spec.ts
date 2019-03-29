/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { EbCardTestModule } from '../../../test.module';
import { EBCardDeleteDialogComponent } from 'app/entities/eb-card/eb-card-delete-dialog.component';
import { EBCardService } from 'app/entities/eb-card/eb-card.service';

describe('Component Tests', () => {
    describe('EBCard Management Delete Component', () => {
        let comp: EBCardDeleteDialogComponent;
        let fixture: ComponentFixture<EBCardDeleteDialogComponent>;
        let service: EBCardService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [EbCardTestModule],
                declarations: [EBCardDeleteDialogComponent]
            })
                .overrideTemplate(EBCardDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(EBCardDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(EBCardService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete', inject(
                [],
                fakeAsync(() => {
                    // GIVEN
                    spyOn(service, 'delete').and.returnValue(of({}));

                    // WHEN
                    comp.confirmDelete(123);
                    tick();

                    // THEN
                    expect(service.delete).toHaveBeenCalledWith(123);
                    expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                })
            ));
        });
    });
});
