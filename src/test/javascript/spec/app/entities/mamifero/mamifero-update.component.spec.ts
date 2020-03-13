import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { Mamiferos2020TestModule } from '../../../test.module';
import { MamiferoUpdateComponent } from 'app/entities/mamifero/mamifero-update.component';
import { MamiferoService } from 'app/entities/mamifero/mamifero.service';
import { Mamifero } from 'app/shared/model/mamifero.model';

describe('Component Tests', () => {
  describe('Mamifero Management Update Component', () => {
    let comp: MamiferoUpdateComponent;
    let fixture: ComponentFixture<MamiferoUpdateComponent>;
    let service: MamiferoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Mamiferos2020TestModule],
        declarations: [MamiferoUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(MamiferoUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(MamiferoUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(MamiferoService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Mamifero(123);
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
        const entity = new Mamifero();
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
