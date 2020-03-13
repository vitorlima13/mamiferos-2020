import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { Mamiferos2020TestModule } from '../../../test.module';
import { ObservacaoAnimalUpdateComponent } from 'app/entities/observacao-animal/observacao-animal-update.component';
import { ObservacaoAnimalService } from 'app/entities/observacao-animal/observacao-animal.service';
import { ObservacaoAnimal } from 'app/shared/model/observacao-animal.model';

describe('Component Tests', () => {
  describe('ObservacaoAnimal Management Update Component', () => {
    let comp: ObservacaoAnimalUpdateComponent;
    let fixture: ComponentFixture<ObservacaoAnimalUpdateComponent>;
    let service: ObservacaoAnimalService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Mamiferos2020TestModule],
        declarations: [ObservacaoAnimalUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ObservacaoAnimalUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ObservacaoAnimalUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ObservacaoAnimalService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ObservacaoAnimal(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new ObservacaoAnimal();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
