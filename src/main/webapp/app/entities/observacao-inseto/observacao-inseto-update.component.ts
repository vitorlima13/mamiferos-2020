import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IObservacaoInseto, ObservacaoInseto } from 'app/shared/model/observacao-inseto.model';
import { ObservacaoInsetoService } from './observacao-inseto.service';

@Component({
  selector: 'jhi-observacao-inseto-update',
  templateUrl: './observacao-inseto-update.component.html'
})
export class ObservacaoInsetoUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    data: [],
    local: [],
    quantidade: []
  });

  constructor(
    protected observacaoInsetoService: ObservacaoInsetoService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ observacaoInseto }) => {
      if (!observacaoInseto.id) {
        const today = moment().startOf('day');
        observacaoInseto.data = today;
      }

      this.updateForm(observacaoInseto);
    });
  }

  updateForm(observacaoInseto: IObservacaoInseto): void {
    this.editForm.patchValue({
      id: observacaoInseto.id,
      data: observacaoInseto.data ? observacaoInseto.data.format(DATE_TIME_FORMAT) : null,
      local: observacaoInseto.local,
      quantidade: observacaoInseto.quantidade
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const observacaoInseto = this.createFromForm();
    if (observacaoInseto.id !== undefined) {
      this.subscribeToSaveResponse(this.observacaoInsetoService.update(observacaoInseto));
    } else {
      this.subscribeToSaveResponse(this.observacaoInsetoService.create(observacaoInseto));
    }
  }

  private createFromForm(): IObservacaoInseto {
    return {
      ...new ObservacaoInseto(),
      id: this.editForm.get(['id'])!.value,
      data: this.editForm.get(['data'])!.value ? moment(this.editForm.get(['data'])!.value, DATE_TIME_FORMAT) : undefined,
      local: this.editForm.get(['local'])!.value,
      quantidade: this.editForm.get(['quantidade'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IObservacaoInseto>>): void {
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
