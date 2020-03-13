import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { Mamiferos2020TestModule } from '../../../test.module';
import { MamiferoDetailComponent } from 'app/entities/mamifero/mamifero-detail.component';
import { Mamifero } from 'app/shared/model/mamifero.model';

describe('Component Tests', () => {
  describe('Mamifero Management Detail Component', () => {
    let comp: MamiferoDetailComponent;
    let fixture: ComponentFixture<MamiferoDetailComponent>;
    const route = ({ data: of({ mamifero: new Mamifero(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Mamiferos2020TestModule],
        declarations: [MamiferoDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(MamiferoDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(MamiferoDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load mamifero on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.mamifero).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
