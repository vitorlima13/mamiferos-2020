import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { Mamiferos2020SharedModule } from 'app/shared/shared.module';
import { MamiferoComponent } from './mamifero.component';
import { MamiferoDetailComponent } from './mamifero-detail.component';
import { MamiferoUpdateComponent } from './mamifero-update.component';
import { MamiferoDeleteDialogComponent } from './mamifero-delete-dialog.component';
import { mamiferoRoute } from './mamifero.route';

@NgModule({
  imports: [Mamiferos2020SharedModule, RouterModule.forChild(mamiferoRoute)],
  declarations: [MamiferoComponent, MamiferoDetailComponent, MamiferoUpdateComponent, MamiferoDeleteDialogComponent],
  entryComponents: [MamiferoDeleteDialogComponent]
})
export class Mamiferos2020MamiferoModule {}
