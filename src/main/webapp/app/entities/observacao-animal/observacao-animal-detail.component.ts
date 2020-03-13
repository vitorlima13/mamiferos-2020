import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IObservacaoAnimal } from 'app/shared/model/observacao-animal.model';

@Component({
  selector: 'jhi-observacao-animal-detail',
  templateUrl: './observacao-animal-detail.component.html'
})
export class ObservacaoAnimalDetailComponent implements OnInit {
  observacaoAnimal: IObservacaoAnimal | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ observacaoAnimal }) => (this.observacaoAnimal = observacaoAnimal));
  }

  previousState(): void {
    window.history.back();
  }
}
