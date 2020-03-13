import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IObservacaoInseto } from 'app/shared/model/observacao-inseto.model';
import { ObservacaoInsetoService } from './observacao-inseto.service';

@Component({
  templateUrl: './observacao-inseto-delete-dialog.component.html'
})
export class ObservacaoInsetoDeleteDialogComponent {
  observacaoInseto?: IObservacaoInseto;

  constructor(
    protected observacaoInsetoService: ObservacaoInsetoService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.observacaoInsetoService.delete(id).subscribe(() => {
      this.eventManager.broadcast('observacaoInsetoListModification');
      this.activeModal.close();
    });
  }
}
