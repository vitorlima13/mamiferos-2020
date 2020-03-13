import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IObservacaoInseto } from 'app/shared/model/observacao-inseto.model';

type EntityResponseType = HttpResponse<IObservacaoInseto>;
type EntityArrayResponseType = HttpResponse<IObservacaoInseto[]>;

@Injectable({ providedIn: 'root' })
export class ObservacaoInsetoService {
  public resourceUrl = SERVER_API_URL + 'api/observacao-insetos';

  constructor(protected http: HttpClient) {}

  create(observacaoInseto: IObservacaoInseto): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(observacaoInseto);
    return this.http
      .post<IObservacaoInseto>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(observacaoInseto: IObservacaoInseto): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(observacaoInseto);
    return this.http
      .put<IObservacaoInseto>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IObservacaoInseto>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IObservacaoInseto[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(observacaoInseto: IObservacaoInseto): IObservacaoInseto {
    const copy: IObservacaoInseto = Object.assign({}, observacaoInseto, {
      data: observacaoInseto.data && observacaoInseto.data.isValid() ? observacaoInseto.data.toJSON() : undefined
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
      res.body.forEach((observacaoInseto: IObservacaoInseto) => {
        observacaoInseto.data = observacaoInseto.data ? moment(observacaoInseto.data) : undefined;
      });
    }
    return res;
  }
}
