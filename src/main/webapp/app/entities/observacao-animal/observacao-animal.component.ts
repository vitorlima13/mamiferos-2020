import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IObservacaoAnimal } from 'app/shared/model/observacao-animal.model';
import { ObservacaoAnimalService } from './observacao-animal.service';
import { ObservacaoAnimalDeleteDialogComponent } from './observacao-animal-delete-dialog.component';

@Component({
  selector: 'jhi-observacao-animal',
  templateUrl: './observacao-animal.component.html'
})
export class ObservacaoAnimalComponent implements OnInit, OnDestroy {
  observacaoAnimals?: IObservacaoAnimal[];
  eventSubscriber?: Subscription;

  constructor(
    protected observacaoAnimalService: ObservacaoAnimalService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.observacaoAnimalService.query().subscribe((res: HttpResponse<IObservacaoAnimal[]>) => (this.observacaoAnimals = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInObservacaoAnimals();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IObservacaoAnimal): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInObservacaoAnimals(): void {
    this.eventSubscriber = this.eventManager.subscribe('observacaoAnimalListModification', () => this.loadAll());
  }

  delete(observacaoAnimal: IObservacaoAnimal): void {
    const modalRef = this.modalService.open(ObservacaoAnimalDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.observacaoAnimal = observacaoAnimal;
  }
}
