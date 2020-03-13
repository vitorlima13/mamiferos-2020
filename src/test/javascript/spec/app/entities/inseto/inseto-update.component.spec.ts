import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { Mamiferos2020TestModule } from '../../../test.module';
import { InsetoUpdateComponent } from 'app/entities/inseto/inseto-update.component';
import { InsetoService } from 'app/entities/inseto/inseto.service';
import { Inseto } from 'app/shared/model/inseto.model';

describe('Component Tests', () => {
  describe('Inseto Management Update Component', () => {
    let comp: InsetoUpdateComponent;
    let fixture: ComponentFixture<InsetoUpdateComponent>;
    let service: InsetoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Mamiferos2020TestModule],
        declarations: [InsetoUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(InsetoUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(InsetoUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(InsetoService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Inseto(123);
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
        const entity = new Inseto();
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
