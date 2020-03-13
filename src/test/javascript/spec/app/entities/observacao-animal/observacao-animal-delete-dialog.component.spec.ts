import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { Mamiferos2020TestModule } from '../../../test.module';
import { MockEventManager } from '../../../helpers/mock-event-manager.service';
import { MockActiveModal } from '../../../helpers/mock-active-modal.service';
import { ObservacaoAnimalDeleteDialogComponent } from 'app/entities/observacao-animal/observacao-animal-delete-dialog.component';
import { ObservacaoAnimalService } from 'app/entities/observacao-animal/observacao-animal.service';

describe('Component Tests', () => {
  describe('ObservacaoAnimal Management Delete Component', () => {
    let comp: ObservacaoAnimalDeleteDialogComponent;
    let fixture: ComponentFixture<ObservacaoAnimalDeleteDialogComponent>;
    let service: ObservacaoAnimalService;
    let mockEventManager: MockEventManager;
    let mockActiveModal: MockActiveModal;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Mamiferos2020TestModule],
        declarations: [ObservacaoAnimalDeleteDialogComponent]
      })
        .overrideTemplate(ObservacaoAnimalDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ObservacaoAnimalDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ObservacaoAnimalService);
      mockEventManager = TestBed.get(JhiEventManager);
      mockActiveModal = TestBed.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.closeSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));

      it('Should not call delete service on clear', () => {
        // GIVEN
        spyOn(service, 'delete');

        // WHEN
        comp.cancel();

        // THEN
        expect(service.delete).not.toHaveBeenCalled();
        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
      });
    });
  });
});
