import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IOperacion, getOperacionIdentifier } from '../operacion.model';

export type EntityResponseType = HttpResponse<IOperacion>;
export type EntityArrayResponseType = HttpResponse<IOperacion[]>;

@Injectable({ providedIn: 'root' })
export class OperacionService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/operacions');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(operacion: IOperacion): Observable<EntityResponseType> {
    return this.http.post<IOperacion>(this.resourceUrl, operacion, { observe: 'response' });
  }

  update(operacion: IOperacion): Observable<EntityResponseType> {
    return this.http.put<IOperacion>(`${this.resourceUrl}/${getOperacionIdentifier(operacion) as number}`, operacion, {
      observe: 'response',
    });
  }

  partialUpdate(operacion: IOperacion): Observable<EntityResponseType> {
    return this.http.patch<IOperacion>(`${this.resourceUrl}/${getOperacionIdentifier(operacion) as number}`, operacion, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IOperacion>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IOperacion[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addOperacionToCollectionIfMissing(
    operacionCollection: IOperacion[],
    ...operacionsToCheck: (IOperacion | null | undefined)[]
  ): IOperacion[] {
    const operacions: IOperacion[] = operacionsToCheck.filter(isPresent);
    if (operacions.length > 0) {
      const operacionCollectionIdentifiers = operacionCollection.map(operacionItem => getOperacionIdentifier(operacionItem)!);
      const operacionsToAdd = operacions.filter(operacionItem => {
        const operacionIdentifier = getOperacionIdentifier(operacionItem);
        if (operacionIdentifier == null || operacionCollectionIdentifiers.includes(operacionIdentifier)) {
          return false;
        }
        operacionCollectionIdentifiers.push(operacionIdentifier);
        return true;
      });
      return [...operacionsToAdd, ...operacionCollection];
    }
    return operacionCollection;
  }
}
