import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IInseto, Inseto } from 'app/shared/model/inseto.model';
import { InsetoService } from './inseto.service';
import { InsetoComponent } from './inseto.component';
import { InsetoDetailComponent } from './inseto-detail.component';
import { InsetoUpdateComponent } from './inseto-update.component';

@Injectable({ providedIn: 'root' })
export class InsetoResolve implements Resolve<IInseto> {
  constructor(private service: InsetoService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IInseto> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((inseto: HttpResponse<Inseto>) => {
          if (inseto.body) {
            return of(inseto.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Inseto());
  }
}

export const insetoRoute: Routes = [
  {
    path: '',
    component: InsetoComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'mamiferos2020App.inseto.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: InsetoDetailComponent,
    resolve: {
      inseto: InsetoResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'mamiferos2020App.inseto.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: InsetoUpdateComponent,
    resolve: {
      inseto: InsetoResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'mamiferos2020App.inseto.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: InsetoUpdateComponent,
    resolve: {
      inseto: InsetoResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'mamiferos2020App.inseto.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
