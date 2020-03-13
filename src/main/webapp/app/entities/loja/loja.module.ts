import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { Mamiferos2020SharedModule } from 'app/shared/shared.module';
import { LojaComponent } from './loja.component';
import { LojaDetailComponent } from './loja-detail.component';
import { LojaUpdateComponent } from './loja-update.component';
import { LojaDeleteDialogComponent } from './loja-delete-dialog.component';
import { lojaRoute } from './loja.route';

@NgModule({
  imports: [Mamiferos2020SharedModule, RouterModule.forChild(lojaRoute)],
  declarations: [LojaComponent, LojaDetailComponent, LojaUpdateComponent, LojaDeleteDialogComponent],
  entryComponents: [LojaDeleteDialogComponent]
})
export class Mamiferos2020LojaModule {}
