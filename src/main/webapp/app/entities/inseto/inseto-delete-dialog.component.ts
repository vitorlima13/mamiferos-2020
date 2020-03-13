import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IInseto } from 'app/shared/model/inseto.model';
import { InsetoService } from './inseto.service';

@Component({
  templateUrl: './inseto-delete-dialog.component.html'
})
export class InsetoDeleteDialogComponent {
  inseto?: IInseto;

  constructor(protected insetoService: InsetoService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.insetoService.delete(id).subscribe(() => {
      this.eventManager.broadcast('insetoListModification');
      this.activeModal.close();
    });
  }
}
