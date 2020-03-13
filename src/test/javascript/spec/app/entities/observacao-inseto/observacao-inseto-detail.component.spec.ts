import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { Mamiferos2020TestModule } from '../../../test.module';
import { ObservacaoInsetoDetailComponent } from 'app/entities/observacao-inseto/observacao-inseto-detail.component';
import { ObservacaoInseto } from 'app/shared/model/observacao-inseto.model';

describe('Component Tests', () => {
  describe('ObservacaoInseto Management Detail Component', () => {
    let comp: ObservacaoInsetoDetailComponent;
    let fixture: ComponentFixture<ObservacaoInsetoDetailComponent>;
    const route = ({ data: of({ observacaoInseto: new ObservacaoInseto(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Mamiferos2020TestModule],
        declarations: [ObservacaoInsetoDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ObservacaoInsetoDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ObservacaoInsetoDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load observacaoInseto on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.observacaoInseto).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
