/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { EbCardTestModule } from '../../../test.module';
import { SocialContactDetailComponent } from 'app/entities/social-contact/social-contact-detail.component';
import { SocialContact } from 'app/shared/model/social-contact.model';

describe('Component Tests', () => {
    describe('SocialContact Management Detail Component', () => {
        let comp: SocialContactDetailComponent;
        let fixture: ComponentFixture<SocialContactDetailComponent>;
        const route = ({ data: of({ socialContact: new SocialContact(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [EbCardTestModule],
                declarations: [SocialContactDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(SocialContactDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(SocialContactDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.socialContact).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
