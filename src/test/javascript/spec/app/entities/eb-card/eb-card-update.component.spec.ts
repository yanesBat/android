/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { EbCardTestModule } from '../../../test.module';
import { EBCardUpdateComponent } from 'app/entities/eb-card/eb-card-update.component';
import { EBCardService } from 'app/entities/eb-card/eb-card.service';
import { EBCard } from 'app/shared/model/eb-card.model';

describe('Component Tests', () => {
    describe('EBCard Management Update Component', () => {
        let comp: EBCardUpdateComponent;
        let fixture: ComponentFixture<EBCardUpdateComponent>;
        let service: EBCardService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [EbCardTestModule],
                declarations: [EBCardUpdateComponent]
            })
                .overrideTemplate(EBCardUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(EBCardUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(EBCardService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new EBCard(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.eBCard = entity;
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
                    const entity = new EBCard();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.eBCard = entity;
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
