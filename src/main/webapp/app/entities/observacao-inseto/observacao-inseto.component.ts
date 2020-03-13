import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IObservacaoInseto } from 'app/shared/model/observacao-inseto.model';
import { ObservacaoInsetoService } from './observacao-inseto.service';
import { ObservacaoInsetoDeleteDialogComponent } from './observacao-inseto-delete-dialog.component';

@Component({
  selector: 'jhi-observacao-inseto',
  templateUrl: './observacao-inseto.component.html'
})
export class ObservacaoInsetoComponent implements OnInit, OnDestroy {
  observacaoInsetos?: IObservacaoInseto[];
  eventSubscriber?: Subscription;

  constructor(
    protected observacaoInsetoService: ObservacaoInsetoService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.observacaoInsetoService.query().subscribe((res: HttpResponse<IObservacaoInseto[]>) => (this.observacaoInsetos = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInObservacaoInsetos();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IObservacaoInseto): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInObservacaoInsetos(): void {
    this.eventSubscriber = this.eventManager.subscribe('observacaoInsetoListModification', () => this.loadAll());
  }

  delete(observacaoInseto: IObservacaoInseto): void {
    const modalRef = this.modalService.open(ObservacaoInsetoDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.observacaoInseto = observacaoInseto;
  }
}
