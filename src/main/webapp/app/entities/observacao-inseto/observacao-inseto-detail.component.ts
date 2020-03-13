import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IObservacaoInseto } from 'app/shared/model/observacao-inseto.model';

@Component({
  selector: 'jhi-observacao-inseto-detail',
  templateUrl: './observacao-inseto-detail.component.html'
})
export class ObservacaoInsetoDetailComponent implements OnInit {
  observacaoInseto: IObservacaoInseto | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ observacaoInseto }) => (this.observacaoInseto = observacaoInseto));
  }

  previousState(): void {
    window.history.back();
  }
}
