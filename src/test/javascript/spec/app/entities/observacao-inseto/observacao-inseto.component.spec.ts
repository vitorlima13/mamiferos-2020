import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { Mamiferos2020TestModule } from '../../../test.module';
import { ObservacaoInsetoComponent } from 'app/entities/observacao-inseto/observacao-inseto.component';
import { ObservacaoInsetoService } from 'app/entities/observacao-inseto/observacao-inseto.service';
import { ObservacaoInseto } from 'app/shared/model/observacao-inseto.model';

describe('Component Tests', () => {
  describe('ObservacaoInseto Management Component', () => {
    let comp: ObservacaoInsetoComponent;
    let fixture: ComponentFixture<ObservacaoInsetoComponent>;
    let service: ObservacaoInsetoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Mamiferos2020TestModule],
        declarations: [ObservacaoInsetoComponent]
      })
        .overrideTemplate(ObservacaoInsetoComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ObservacaoInsetoComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ObservacaoInsetoService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new ObservacaoInseto(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.observacaoInsetos && comp.observacaoInsetos[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
