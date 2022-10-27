import { IAuditoria } from 'app/entities/auditoria/auditoria.model';

export interface IOperacion {
  id?: number;
  numeroOperacion?: string | null;
  monto?: number | null;
  cuentaOrigen?: number | null;
  cuentaDestino?: number | null;
  auditoria?: IAuditoria | null;
}

export class Operacion implements IOperacion {
  constructor(
    public id?: number,
    public numeroOperacion?: string | null,
    public monto?: number | null,
    public cuentaOrigen?: number | null,
    public cuentaDestino?: number | null,
    public auditoria?: IAuditoria | null
  ) {}
}

export function getOperacionIdentifier(operacion: IOperacion): number | undefined {
  return operacion.id;
}
