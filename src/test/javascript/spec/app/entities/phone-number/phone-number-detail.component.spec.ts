/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { EbCardTestModule } from '../../../test.module';
import { PhoneNumberDetailComponent } from 'app/entities/phone-number/phone-number-detail.component';
import { PhoneNumber } from 'app/shared/model/phone-number.model';

describe('Component Tests', () => {
    describe('PhoneNumber Management Detail Component', () => {
        let comp: PhoneNumberDetailComponent;
        let fixture: ComponentFixture<PhoneNumberDetailComponent>;
        const route = ({ data: of({ phoneNumber: new PhoneNumber(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [EbCardTestModule],
                declarations: [PhoneNumberDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(PhoneNumberDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(PhoneNumberDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.phoneNumber).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
