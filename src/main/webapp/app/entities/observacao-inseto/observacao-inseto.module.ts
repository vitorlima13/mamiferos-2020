import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { Mamiferos2020SharedModule } from 'app/shared/shared.module';
import { ObservacaoInsetoComponent } from './observacao-inseto.component';
import { ObservacaoInsetoDetailComponent } from './observacao-inseto-detail.component';
import { ObservacaoInsetoUpdateComponent } from './observacao-inseto-update.component';
import { ObservacaoInsetoDeleteDialogComponent } from './observacao-inseto-delete-dialog.component';
import { observacaoInsetoRoute } from './observacao-inseto.route';

@NgModule({
  imports: [Mamiferos2020SharedModule, RouterModule.forChild(observacaoInsetoRoute)],
  declarations: [
    ObservacaoInsetoComponent,
    ObservacaoInsetoDetailComponent,
    ObservacaoInsetoUpdateComponent,
    ObservacaoInsetoDeleteDialogComponent
  ],
  entryComponents: [ObservacaoInsetoDeleteDialogComponent]
})
export class Mamiferos2020ObservacaoInsetoModule {}
