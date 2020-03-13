import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IObservacaoAnimal, ObservacaoAnimal } from 'app/shared/model/observacao-animal.model';
import { ObservacaoAnimalService } from './observacao-animal.service';
import { ObservacaoAnimalComponent } from './observacao-animal.component';
import { ObservacaoAnimalDetailComponent } from './observacao-animal-detail.component';
import { ObservacaoAnimalUpdateComponent } from './observacao-animal-update.component';

@Injectable({ providedIn: 'root' })
export class ObservacaoAnimalResolve implements Resolve<IObservacaoAnimal> {
  constructor(private service: ObservacaoAnimalService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IObservacaoAnimal> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((observacaoAnimal: HttpResponse<ObservacaoAnimal>) => {
          if (observacaoAnimal.body) {
            return of(observacaoAnimal.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new ObservacaoAnimal());
  }
}

export const observacaoAnimalRoute: Routes = [
  {
    path: '',
    component: ObservacaoAnimalComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'mamiferos2020App.observacaoAnimal.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ObservacaoAnimalDetailComponent,
    resolve: {
      observacaoAnimal: ObservacaoAnimalResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'mamiferos2020App.observacaoAnimal.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ObservacaoAnimalUpdateComponent,
    resolve: {
      observacaoAnimal: ObservacaoAnimalResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'mamiferos2020App.observacaoAnimal.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ObservacaoAnimalUpdateComponent,
    resolve: {
      observacaoAnimal: ObservacaoAnimalResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'mamiferos2020App.observacaoAnimal.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
