/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { EbCardTestModule } from '../../../test.module';
import { SocialContactUpdateComponent } from 'app/entities/social-contact/social-contact-update.component';
import { SocialContactService } from 'app/entities/social-contact/social-contact.service';
import { SocialContact } from 'app/shared/model/social-contact.model';

describe('Component Tests', () => {
    describe('SocialContact Management Update Component', () => {
        let comp: SocialContactUpdateComponent;
        let fixture: ComponentFixture<SocialContactUpdateComponent>;
        let service: SocialContactService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [EbCardTestModule],
                declarations: [SocialContactUpdateComponent]
            })
                .overrideTemplate(SocialContactUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(SocialContactUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SocialContactService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new SocialContact(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.socialContact = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.update).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );

            it(
                'Should call create service on save for new entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new SocialContact();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.socialContact = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.create).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );
        });
    });
});
