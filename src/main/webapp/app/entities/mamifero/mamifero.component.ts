import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IMamifero } from 'app/shared/model/mamifero.model';
import { MamiferoService } from './mamifero.service';
import { MamiferoDeleteDialogComponent } from './mamifero-delete-dialog.component';

@Component({
  selector: 'jhi-mamifero',
  templateUrl: './mamifero.component.html'
})
export class MamiferoComponent implements OnInit, OnDestroy {
  mamiferos?: IMamifero[];
  eventSubscriber?: Subscription;

  constructor(protected mamiferoService: MamiferoService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.mamiferoService.query().subscribe((res: HttpResponse<IMamifero[]>) => (this.mamiferos = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInMamiferos();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IMamifero): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInMamiferos(): void {
    this.eventSubscriber = this.eventManager.subscribe('mamiferoListModification', () => this.loadAll());
  }

  delete(mamifero: IMamifero): void {
    const modalRef = this.modalService.open(MamiferoDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.mamifero = mamifero;
  }
}
