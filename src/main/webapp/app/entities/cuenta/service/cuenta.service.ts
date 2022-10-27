import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ICuenta, getCuentaIdentifier } from '../cuenta.model';

export type EntityResponseType = HttpResponse<ICuenta>;
export type EntityArrayResponseType = HttpResponse<ICuenta[]>;

@Injectable({ providedIn: 'root' })
export class CuentaService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/cuentas');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(cuenta: ICuenta): Observable<EntityResponseType> {
    return this.http.post<ICuenta>(this.resourceUrl, cuenta, { observe: 'response' });
  }

  update(cuenta: ICuenta): Observable<EntityResponseType> {
    return this.http.put<ICuenta>(`${this.resourceUrl}/${getCuentaIdentifier(cuenta) as number}`, cuenta, { observe: 'response' });
  }

  partialUpdate(cuenta: ICuenta): Observable<EntityResponseType> {
    return this.http.patch<ICuenta>(`${this.resourceUrl}/${getCuentaIdentifier(cuenta) as number}`, cuenta, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ICuenta>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICuenta[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addCuentaToCollectionIfMissing(cuentaCollection: ICuenta[], ...cuentasToCheck: (ICuenta | null | undefined)[]): ICuenta[] {
    const cuentas: ICuenta[] = cuentasToCheck.filter(isPresent);
    if (cuentas.length > 0) {
      const cuentaCollectionIdentifiers = cuentaCollection.map(cuentaItem => getCuentaIdentifier(cuentaItem)!);
      const cuentasToAdd = cuentas.filter(cuentaItem => {
        const cuentaIdentifier = getCuentaIdentifier(cuentaItem);
        if (cuentaIdentifier == null || cuentaCollectionIdentifiers.includes(cuentaIdentifier)) {
          return false;
        }
        cuentaCollectionIdentifiers.push(cuentaIdentifier);
        return true;
      });
      return [...cuentasToAdd, ...cuentaCollection];
    }
    return cuentaCollection;
  }
}
