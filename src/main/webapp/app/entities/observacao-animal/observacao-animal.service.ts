import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IObservacaoAnimal } from 'app/shared/model/observacao-animal.model';

type EntityResponseType = HttpResponse<IObservacaoAnimal>;
type EntityArrayResponseType = HttpResponse<IObservacaoAnimal[]>;

@Injectable({ providedIn: 'root' })
export class ObservacaoAnimalService {
  public resourceUrl = SERVER_API_URL + 'api/observacao-animals';

  constructor(protected http: HttpClient) {}

  create(observacaoAnimal: IObservacaoAnimal): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(observacaoAnimal);
    return this.http
      .post<IObservacaoAnimal>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(observacaoAnimal: IObservacaoAnimal): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(observacaoAnimal);
    return this.http
      .put<IObservacaoAnimal>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IObservacaoAnimal>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IObservacaoAnimal[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(observacaoAnimal: IObservacaoAnimal): IObservacaoAnimal {
    const copy: IObservacaoAnimal = Object.assign({}, observacaoAnimal, {
      data: observacaoAnimal.data && observacaoAnimal.data.isValid() ? observacaoAnimal.data.toJSON() : undefined
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.data = res.body.data ? moment(res.body.data) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((observacaoAnimal: IObservacaoAnimal) => {
        observacaoAnimal.data = observacaoAnimal.data ? moment(observacaoAnimal.data) : undefined;
      });
    }
    return res;
  }
}
