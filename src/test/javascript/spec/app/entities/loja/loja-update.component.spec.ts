import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { Mamiferos2020TestModule } from '../../../test.module';
import { LojaUpdateComponent } from 'app/entities/loja/loja-update.component';
import { LojaService } from 'app/entities/loja/loja.service';
import { Loja } from 'app/shared/model/loja.model';

describe('Component Tests', () => {
  describe('Loja Management Update Component', () => {
    let comp: LojaUpdateComponent;
    let fixture: ComponentFixture<LojaUpdateComponent>;
    let service: LojaService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Mamiferos2020TestModule],
        declarations: [LojaUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(LojaUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(LojaUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(LojaService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Loja(123);
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
        const entity = new Loja();
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
