import { IAuditoria } from 'app/entities/auditoria/auditoria.model';
import { TipoCuenta } from 'app/entities/enumerations/tipo-cuenta.model';

export interface ICuenta {
  id?: number;
  numeroCuenta?: string | null;
  saldo?: number | null;
  tipoCuenta?: TipoCuenta | null;
  auditoria?: IAuditoria | null;
}

export class Cuenta implements ICuenta {
  constructor(
    public id?: number,
    public numeroCuenta?: string | null,
    public saldo?: number | null,
    public tipoCuenta?: TipoCuenta | null,
    public auditoria?: IAuditoria | null
  ) {}
}

export function getCuentaIdentifier(cuenta: ICuenta): number | undefined {
  return cuenta.id;
}
