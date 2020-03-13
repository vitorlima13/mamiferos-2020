import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IInseto } from 'app/shared/model/inseto.model';

@Component({
  selector: 'jhi-inseto-detail',
  templateUrl: './inseto-detail.component.html'
})
export class InsetoDetailComponent implements OnInit {
  inseto: IInseto | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ inseto }) => (this.inseto = inseto));
  }

  previousState(): void {
    window.history.back();
  }
}
