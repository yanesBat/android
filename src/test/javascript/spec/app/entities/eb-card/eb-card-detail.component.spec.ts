/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { EbCardTestModule } from '../../../test.module';
import { EBCardDetailComponent } from 'app/entities/eb-card/eb-card-detail.component';
import { EBCard } from 'app/shared/model/eb-card.model';

describe('Component Tests', () => {
    describe('EBCard Management Detail Component', () => {
        let comp: EBCardDetailComponent;
        let fixture: ComponentFixture<EBCardDetailComponent>;
        const route = ({ data: of({ eBCard: new EBCard(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [EbCardTestModule],
                declarations: [EBCardDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(EBCardDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(EBCardDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.eBCard).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
