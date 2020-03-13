import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { Mamiferos2020TestModule } from '../../../test.module';
import { LojaComponent } from 'app/entities/loja/loja.component';
import { LojaService } from 'app/entities/loja/loja.service';
import { Loja } from 'app/shared/model/loja.model';

describe('Component Tests', () => {
  describe('Loja Management Component', () => {
    let comp: LojaComponent;
    let fixture: ComponentFixture<LojaComponent>;
    let service: LojaService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Mamiferos2020TestModule],
        declarations: [LojaComponent]
      })
        .overrideTemplate(LojaComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(LojaComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(LojaService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Loja(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.lojas && comp.lojas[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
