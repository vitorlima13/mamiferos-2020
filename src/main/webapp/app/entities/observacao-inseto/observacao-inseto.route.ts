import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IObservacaoInseto, ObservacaoInseto } from 'app/shared/model/observacao-inseto.model';
import { ObservacaoInsetoService } from './observacao-inseto.service';
import { ObservacaoInsetoComponent } from './observacao-inseto.component';
import { ObservacaoInsetoDetailComponent } from './observacao-inseto-detail.component';
import { ObservacaoInsetoUpdateComponent } from './observacao-inseto-update.component';

@Injectable({ providedIn: 'root' })
export class ObservacaoInsetoResolve implements Resolve<IObservacaoInseto> {
  constructor(private service: ObservacaoInsetoService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IObservacaoInseto> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((observacaoInseto: HttpResponse<ObservacaoInseto>) => {
          if (observacaoInseto.body) {
            return of(observacaoInseto.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new ObservacaoInseto());
  }
}

export const observacaoInsetoRoute: Routes = [
  {
    path: '',
    component: ObservacaoInsetoComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'mamiferos2020App.observacaoInseto.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ObservacaoInsetoDetailComponent,
    resolve: {
      observacaoInseto: ObservacaoInsetoResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'mamiferos2020App.observacaoInseto.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ObservacaoInsetoUpdateComponent,
    resolve: {
      observacaoInseto: ObservacaoInsetoResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'mamiferos2020App.observacaoInseto.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ObservacaoInsetoUpdateComponent,
    resolve: {
      observacaoInseto: ObservacaoInsetoResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'mamiferos2020App.observacaoInseto.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
