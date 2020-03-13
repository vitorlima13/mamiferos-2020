import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { Mamiferos2020SharedModule } from 'app/shared/shared.module';

import { LogsComponent } from './logs.component';

import { logsRoute } from './logs.route';

@NgModule({
  imports: [Mamiferos2020SharedModule, RouterModule.forChild([logsRoute])],
  declarations: [LogsComponent]
})
export class LogsModule {}
