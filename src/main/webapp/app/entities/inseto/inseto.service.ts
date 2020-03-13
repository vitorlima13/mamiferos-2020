import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IInseto } from 'app/shared/model/inseto.model';

type EntityResponseType = HttpResponse<IInseto>;
type EntityArrayResponseType = HttpResponse<IInseto[]>;

@Injectable({ providedIn: 'root' })
export class InsetoService {
  public resourceUrl = SERVER_API_URL + 'api/insetos';

  constructor(protected http: HttpClient) {}

  create(inseto: IInseto): Observable<EntityResponseType> {
    return this.http.post<IInseto>(this.resourceUrl, inseto, { observe: 'response' });
  }

  update(inseto: IInseto): Observable<EntityResponseType> {
    return this.http.put<IInseto>(this.resourceUrl, inseto, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IInseto>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IInseto[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
