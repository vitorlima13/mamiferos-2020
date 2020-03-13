import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ILoja, Loja } from 'app/shared/model/loja.model';
import { LojaService } from './loja.service';

@Component({
  selector: 'jhi-loja-update',
  templateUrl: './loja-update.component.html'
})
export class LojaUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    nome: []
  });

  constructor(protected lojaService: LojaService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ loja }) => {
      this.updateForm(loja);
    });
  }

  updateForm(loja: ILoja): void {
    this.editForm.patchValue({
      id: loja.id,
      nome: loja.nome
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const loja = this.createFromForm();
    if (loja.id !== undefined) {
      this.subscribeToSaveResponse(this.lojaService.update(loja));
    } else {
      this.subscribeToSaveResponse(this.lojaService.create(loja));
    }
  }

  private createFromForm(): ILoja {
    return {
      ...new Loja(),
      id: this.editForm.get(['id'])!.value,
      nome: this.editForm.get(['nome'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ILoja>>): void {
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
}
