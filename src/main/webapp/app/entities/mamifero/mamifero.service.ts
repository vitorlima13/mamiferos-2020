import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IMamifero } from 'app/shared/model/mamifero.model';

type EntityResponseType = HttpResponse<IMamifero>;
type EntityArrayResponseType = HttpResponse<IMamifero[]>;

@Injectable({ providedIn: 'root' })
export class MamiferoService {
  public resourceUrl = SERVER_API_URL + 'api/mamiferos';

  constructor(protected http: HttpClient) {}

  create(mamifero: IMamifero): Observable<EntityResponseType> {
    return this.http.post<IMamifero>(this.resourceUrl, mamifero, { observe: 'response' });
  }

  update(mamifero: IMamifero): Observable<EntityResponseType> {
    return this.http.put<IMamifero>(this.resourceUrl, mamifero, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IMamifero>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IMamifero[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
