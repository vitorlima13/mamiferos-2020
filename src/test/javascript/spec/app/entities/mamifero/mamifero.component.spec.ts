import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { Mamiferos2020TestModule } from '../../../test.module';
import { MamiferoComponent } from 'app/entities/mamifero/mamifero.component';
import { MamiferoService } from 'app/entities/mamifero/mamifero.service';
import { Mamifero } from 'app/shared/model/mamifero.model';

describe('Component Tests', () => {
  describe('Mamifero Management Component', () => {
    let comp: MamiferoComponent;
    let fixture: ComponentFixture<MamiferoComponent>;
    let service: MamiferoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Mamiferos2020TestModule],
        declarations: [MamiferoComponent]
      })
        .overrideTemplate(MamiferoComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(MamiferoComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(MamiferoService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Mamifero(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.mamiferos && comp.mamiferos[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
