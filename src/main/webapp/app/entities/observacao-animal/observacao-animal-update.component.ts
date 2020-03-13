import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IObservacaoAnimal, ObservacaoAnimal } from 'app/shared/model/observacao-animal.model';
import { ObservacaoAnimalService } from './observacao-animal.service';

@Component({
  selector: 'jhi-observacao-animal-update',
  templateUrl: './observacao-animal-update.component.html'
})
export class ObservacaoAnimalUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    data: [],
    local: [],
    comprimento: [],
    altura: [],
    peso: []
  });

  constructor(
    protected observacaoAnimalService: ObservacaoAnimalService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ observacaoAnimal }) => {
      if (!observacaoAnimal.id) {
        const today = moment().startOf('day');
        observacaoAnimal.data = today;
      }

      this.updateForm(observacaoAnimal);
    });
  }

  updateForm(observacaoAnimal: IObservacaoAnimal): void {
    this.editForm.patchValue({
      id: observacaoAnimal.id,
      data: observacaoAnimal.data ? observacaoAnimal.data.format(DATE_TIME_FORMAT) : null,
      local: observacaoAnimal.local,
      comprimento: observacaoAnimal.comprimento,
      altura: observacaoAnimal.altura,
      peso: observacaoAnimal.peso
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const observacaoAnimal = this.createFromForm();
    if (observacaoAnimal.id !== undefined) {
      this.subscribeToSaveResponse(this.observacaoAnimalService.update(observacaoAnimal));
    } else {
      this.subscribeToSaveResponse(this.observacaoAnimalService.create(observacaoAnimal));
    }
  }

  private createFromForm(): IObservacaoAnimal {
    return {
      ...new ObservacaoAnimal(),
      id: this.editForm.get(['id'])!.value,
      data: this.editForm.get(['data'])!.value ? moment(this.editForm.get(['data'])!.value, DATE_TIME_FORMAT) : undefined,
      local: this.editForm.get(['local'])!.value,
      comprimento: this.editForm.get(['comprimento'])!.value,
      altura: this.editForm.get(['altura'])!.value,
      peso: this.editForm.get(['peso'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IObservacaoAnimal>>): void {
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
