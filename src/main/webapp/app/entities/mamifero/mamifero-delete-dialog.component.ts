import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IMamifero } from 'app/shared/model/mamifero.model';
import { MamiferoService } from './mamifero.service';

@Component({
  templateUrl: './mamifero-delete-dialog.component.html'
})
export class MamiferoDeleteDialogComponent {
  mamifero?: IMamifero;

  constructor(protected mamiferoService: MamiferoService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.mamiferoService.delete(id).subscribe(() => {
      this.eventManager.broadcast('mamiferoListModification');
      this.activeModal.close();
    });
  }
}
