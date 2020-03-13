import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IMamifero } from 'app/shared/model/mamifero.model';

@Component({
  selector: 'jhi-mamifero-detail',
  templateUrl: './mamifero-detail.component.html'
})
export class MamiferoDetailComponent implements OnInit {
  mamifero: IMamifero | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ mamifero }) => (this.mamifero = mamifero));
  }

  previousState(): void {
    window.history.back();
  }
}
