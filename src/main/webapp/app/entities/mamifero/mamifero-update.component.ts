import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IMamifero, Mamifero } from 'app/shared/model/mamifero.model';
import { MamiferoService } from './mamifero.service';
import { ILoja } from 'app/shared/model/loja.model';
import { LojaService } from 'app/entities/loja/loja.service';

type SelectableEntity = IMamifero | ILoja;

@Component({
  selector: 'jhi-mamifero-update',
  templateUrl: './mamifero-update.component.html'
})
export class MamiferoUpdateComponent implements OnInit {
  isSaving = false;
  mamiferos: IMamifero[] = [];
  lojas: ILoja[] = [];

  editForm = this.fb.group({
    id: [],
    nome: [],
    tipo: [],
    apelido: [],
    numero: [],
    altura: [],
    peso: [],
    comprimento: [],
    progenitora: [],
    loja: []
  });

  constructor(
    protected mamiferoService: MamiferoService,
    protected lojaService: LojaService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ mamifero }) => {
      this.updateForm(mamifero);

      this.mamiferoService.query().subscribe((res: HttpResponse<IMamifero[]>) => (this.mamiferos = res.body || []));

      this.lojaService.query().subscribe((res: HttpResponse<ILoja[]>) => (this.lojas = res.body || []));
    });
  }

  updateForm(mamifero: IMamifero): void {
    this.editForm.patchValue({
      id: mamifero.id,
      nome: mamifero.nome,
      tipo: mamifero.tipo,
      apelido: mamifero.apelido,
      numero: mamifero.numero,
      altura: mamifero.altura,
      peso: mamifero.peso,
      comprimento: mamifero.comprimento,
      progenitora: mamifero.progenitora,
      loja: mamifero.loja
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const mamifero = this.createFromForm();
    if (mamifero.id !== undefined) {
      this.subscribeToSaveResponse(this.mamiferoService.update(mamifero));
    } else {
      this.subscribeToSaveResponse(this.mamiferoService.create(mamifero));
    }
  }

  private createFromForm(): IMamifero {
    return {
      ...new Mamifero(),
      id: this.editForm.get(['id'])!.value,
      nome: this.editForm.get(['nome'])!.value,
      tipo: this.editForm.get(['tipo'])!.value,
      apelido: this.editForm.get(['apelido'])!.value,
      numero: this.editForm.get(['numero'])!.value,
      altura: this.editForm.get(['altura'])!.value,
      peso: this.editForm.get(['peso'])!.value,
      comprimento: this.editForm.get(['comprimento'])!.value,
      progenitora: this.editForm.get(['progenitora'])!.value,
      loja: this.editForm.get(['loja'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IMamifero>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }
}
