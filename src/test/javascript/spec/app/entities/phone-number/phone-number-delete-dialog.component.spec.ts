/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { EbCardTestModule } from '../../../test.module';
import { PhoneNumberDeleteDialogComponent } from 'app/entities/phone-number/phone-number-delete-dialog.component';
import { PhoneNumberService } from 'app/entities/phone-number/phone-number.service';

describe('Component Tests', () => {
    describe('PhoneNumber Management Delete Component', () => {
        let comp: PhoneNumberDeleteDialogComponent;
        let fixture: ComponentFixture<PhoneNumberDeleteDialogComponent>;
        let service: PhoneNumberService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [EbCardTestModule],
                declarations: [PhoneNumberDeleteDialogComponent]
            })
                .overrideTemplate(PhoneNumberDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(PhoneNumberDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PhoneNumberService);
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
