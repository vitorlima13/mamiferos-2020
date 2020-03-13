import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IInseto } from 'app/shared/model/inseto.model';
import { InsetoService } from './inseto.service';
import { InsetoDeleteDialogComponent } from './inseto-delete-dialog.component';

@Component({
  selector: 'jhi-inseto',
  templateUrl: './inseto.component.html'
})
export class InsetoComponent implements OnInit, OnDestroy {
  insetos?: IInseto[];
  eventSubscriber?: Subscription;

  constructor(protected insetoService: InsetoService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.insetoService.query().subscribe((res: HttpResponse<IInseto[]>) => (this.insetos = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInInsetos();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IInseto): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInInsetos(): void {
    this.eventSubscriber = this.eventManager.subscribe('insetoListModification', () => this.loadAll());
  }

  delete(inseto: IInseto): void {
    const modalRef = this.modalService.open(InsetoDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.inseto = inseto;
  }
}
