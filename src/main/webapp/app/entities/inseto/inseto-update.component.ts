import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IInseto, Inseto } from 'app/shared/model/inseto.model';
import { InsetoService } from './inseto.service';

@Component({
  selector: 'jhi-inseto-update',
  templateUrl: './inseto-update.component.html'
})
export class InsetoUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    tipo: [],
    numero: [],
    altura: [],
    peso: [],
    comprimento: []
  });

  constructor(protected insetoService: InsetoService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ inseto }) => {
      this.updateForm(inseto);
    });
  }

  updateForm(inseto: IInseto): void {
    this.editForm.patchValue({
      id: inseto.id,
      tipo: inseto.tipo,
      numero: inseto.numero,
      altura: inseto.altura,
      peso: inseto.peso,
      comprimento: inseto.comprimento
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const inseto = this.createFromForm();
    if (inseto.id !== undefined) {
      this.subscribeToSaveResponse(this.insetoService.update(inseto));
    } else {
      this.subscribeToSaveResponse(this.insetoService.create(inseto));
    }
  }

  private createFromForm(): IInseto {
    return {
      ...new Inseto(),
      id: this.editForm.get(['id'])!.value,
      tipo: this.editForm.get(['tipo'])!.value,
      numero: this.editForm.get(['numero'])!.value,
      altura: this.editForm.get(['altura'])!.value,
      peso: this.editForm.get(['peso'])!.value,
      comprimento: this.editForm.get(['comprimento'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IInseto>>): void {
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
