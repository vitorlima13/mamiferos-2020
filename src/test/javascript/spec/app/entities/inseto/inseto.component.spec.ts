import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { Mamiferos2020TestModule } from '../../../test.module';
import { InsetoComponent } from 'app/entities/inseto/inseto.component';
import { InsetoService } from 'app/entities/inseto/inseto.service';
import { Inseto } from 'app/shared/model/inseto.model';

describe('Component Tests', () => {
  describe('Inseto Management Component', () => {
    let comp: InsetoComponent;
    let fixture: ComponentFixture<InsetoComponent>;
    let service: InsetoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Mamiferos2020TestModule],
        declarations: [InsetoComponent]
      })
        .overrideTemplate(InsetoComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(InsetoComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(InsetoService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Inseto(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.insetos && comp.insetos[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
