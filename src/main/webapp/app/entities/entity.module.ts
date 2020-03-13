import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'mamifero',
        loadChildren: () => import('./mamifero/mamifero.module').then(m => m.Mamiferos2020MamiferoModule)
      },
      {
        path: 'inseto',
        loadChildren: () => import('./inseto/inseto.module').then(m => m.Mamiferos2020InsetoModule)
      },
      {
        path: 'observacao-animal',
        loadChildren: () => import('./observacao-animal/observacao-animal.module').then(m => m.Mamiferos2020ObservacaoAnimalModule)
      },
      {
        path: 'observacao-inseto',
        loadChildren: () => import('./observacao-inseto/observacao-inseto.module').then(m => m.Mamiferos2020ObservacaoInsetoModule)
      },
      {
        path: 'loja',
        loadChildren: () => import('./loja/loja.module').then(m => m.Mamiferos2020LojaModule)
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ]
})
export class Mamiferos2020EntityModule {}
