import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { Mamiferos2020TestModule } from '../../../test.module';
import { ObservacaoAnimalComponent } from 'app/entities/observacao-animal/observacao-animal.component';
import { ObservacaoAnimalService } from 'app/entities/observacao-animal/observacao-animal.service';
import { ObservacaoAnimal } from 'app/shared/model/observacao-animal.model';

describe('Component Tests', () => {
  describe('ObservacaoAnimal Management Component', () => {
    let comp: ObservacaoAnimalComponent;
    let fixture: ComponentFixture<ObservacaoAnimalComponent>;
    let service: ObservacaoAnimalService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Mamiferos2020TestModule],
        declarations: [ObservacaoAnimalComponent]
      })
        .overrideTemplate(ObservacaoAnimalComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ObservacaoAnimalComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ObservacaoAnimalService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new ObservacaoAnimal(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.observacaoAnimals && comp.observacaoAnimals[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
