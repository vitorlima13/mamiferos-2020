import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IMamifero, Mamifero } from 'app/shared/model/mamifero.model';
import { MamiferoService } from './mamifero.service';
import { MamiferoComponent } from './mamifero.component';
import { MamiferoDetailComponent } from './mamifero-detail.component';
import { MamiferoUpdateComponent } from './mamifero-update.component';

@Injectable({ providedIn: 'root' })
export class MamiferoResolve implements Resolve<IMamifero> {
  constructor(private service: MamiferoService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IMamifero> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((mamifero: HttpResponse<Mamifero>) => {
          if (mamifero.body) {
            return of(mamifero.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Mamifero());
  }
}

export const mamiferoRoute: Routes = [
  {
    path: '',
    component: MamiferoComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'mamiferos2020App.mamifero.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: MamiferoDetailComponent,
    resolve: {
      mamifero: MamiferoResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'mamiferos2020App.mamifero.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: MamiferoUpdateComponent,
    resolve: {
      mamifero: MamiferoResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'mamiferos2020App.mamifero.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: MamiferoUpdateComponent,
    resolve: {
      mamifero: MamiferoResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'mamiferos2020App.mamifero.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
