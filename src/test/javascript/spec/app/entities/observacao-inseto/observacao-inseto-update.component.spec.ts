import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { Mamiferos2020TestModule } from '../../../test.module';
import { ObservacaoInsetoUpdateComponent } from 'app/entities/observacao-inseto/observacao-inseto-update.component';
import { ObservacaoInsetoService } from 'app/entities/observacao-inseto/observacao-inseto.service';
import { ObservacaoInseto } from 'app/shared/model/observacao-inseto.model';

describe('Component Tests', () => {
  describe('ObservacaoInseto Management Update Component', () => {
    let comp: ObservacaoInsetoUpdateComponent;
    let fixture: ComponentFixture<ObservacaoInsetoUpdateComponent>;
    let service: ObservacaoInsetoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Mamiferos2020TestModule],
        declarations: [ObservacaoInsetoUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ObservacaoInsetoUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ObservacaoInsetoUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ObservacaoInsetoService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ObservacaoInseto(123);
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
        const entity = new ObservacaoInseto();
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
