import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ILoja } from 'app/shared/model/loja.model';

type EntityResponseType = HttpResponse<ILoja>;
type EntityArrayResponseType = HttpResponse<ILoja[]>;

@Injectable({ providedIn: 'root' })
export class LojaService {
  public resourceUrl = SERVER_API_URL + 'api/lojas';

  constructor(protected http: HttpClient) {}

  create(loja: ILoja): Observable<EntityResponseType> {
    return this.http.post<ILoja>(this.resourceUrl, loja, { observe: 'response' });
  }

  update(loja: ILoja): Observable<EntityResponseType> {
    return this.http.put<ILoja>(this.resourceUrl, loja, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ILoja>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ILoja[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
