import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ILoja } from 'app/shared/model/loja.model';
import { LojaService } from './loja.service';
import { LojaDeleteDialogComponent } from './loja-delete-dialog.component';

@Component({
  selector: 'jhi-loja',
  templateUrl: './loja.component.html'
})
export class LojaComponent implements OnInit, OnDestroy {
  lojas?: ILoja[];
  eventSubscriber?: Subscription;

  constructor(protected lojaService: LojaService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.lojaService.query().subscribe((res: HttpResponse<ILoja[]>) => (this.lojas = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInLojas();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ILoja): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInLojas(): void {
    this.eventSubscriber = this.eventManager.subscribe('lojaListModification', () => this.loadAll());
  }

  delete(loja: ILoja): void {
    const modalRef = this.modalService.open(LojaDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.loja = loja;
  }
}
