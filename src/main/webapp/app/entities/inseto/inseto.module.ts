import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { Mamiferos2020SharedModule } from 'app/shared/shared.module';
import { InsetoComponent } from './inseto.component';
import { InsetoDetailComponent } from './inseto-detail.component';
import { InsetoUpdateComponent } from './inseto-update.component';
import { InsetoDeleteDialogComponent } from './inseto-delete-dialog.component';
import { insetoRoute } from './inseto.route';

@NgModule({
  imports: [Mamiferos2020SharedModule, RouterModule.forChild(insetoRoute)],
  declarations: [InsetoComponent, InsetoDetailComponent, InsetoUpdateComponent, InsetoDeleteDialogComponent],
  entryComponents: [InsetoDeleteDialogComponent]
})
export class Mamiferos2020InsetoModule {}
