import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { Mamiferos2020TestModule } from '../../../test.module';
import { LojaDetailComponent } from 'app/entities/loja/loja-detail.component';
import { Loja } from 'app/shared/model/loja.model';

describe('Component Tests', () => {
  describe('Loja Management Detail Component', () => {
    let comp: LojaDetailComponent;
    let fixture: ComponentFixture<LojaDetailComponent>;
    const route = ({ data: of({ loja: new Loja(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Mamiferos2020TestModule],
        declarations: [LojaDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(LojaDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(LojaDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load loja on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.loja).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
