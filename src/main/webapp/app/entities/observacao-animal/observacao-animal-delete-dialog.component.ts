import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IObservacaoAnimal } from 'app/shared/model/observacao-animal.model';
import { ObservacaoAnimalService } from './observacao-animal.service';

@Component({
  templateUrl: './observacao-animal-delete-dialog.component.html'
})
export class ObservacaoAnimalDeleteDialogComponent {
  observacaoAnimal?: IObservacaoAnimal;

  constructor(
    protected observacaoAnimalService: ObservacaoAnimalService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.observacaoAnimalService.delete(id).subscribe(() => {
      this.eventManager.broadcast('observacaoAnimalListModification');
      this.activeModal.close();
    });
  }
}
