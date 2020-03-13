import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { Mamiferos2020SharedModule } from 'app/shared/shared.module';
import { ObservacaoAnimalComponent } from './observacao-animal.component';
import { ObservacaoAnimalDetailComponent } from './observacao-animal-detail.component';
import { ObservacaoAnimalUpdateComponent } from './observacao-animal-update.component';
import { ObservacaoAnimalDeleteDialogComponent } from './observacao-animal-delete-dialog.component';
import { observacaoAnimalRoute } from './observacao-animal.route';

@NgModule({
  imports: [Mamiferos2020SharedModule, RouterModule.forChild(observacaoAnimalRoute)],
  declarations: [
    ObservacaoAnimalComponent,
    ObservacaoAnimalDetailComponent,
    ObservacaoAnimalUpdateComponent,
    ObservacaoAnimalDeleteDialogComponent
  ],
  entryComponents: [ObservacaoAnimalDeleteDialogComponent]
})
export class Mamiferos2020ObservacaoAnimalModule {}
