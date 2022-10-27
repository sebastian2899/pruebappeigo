import { ICuenta } from 'app/entities/cuenta/cuenta.model';
import { IAuditoria } from 'app/entities/auditoria/auditoria.model';

export interface IPersona {
  id?: number;
  nombre?: string | null;
  apellidos?: string | null;
  tipoDocumento?: string | null;
  numeroDocumento?: string | null;
  email?: string | null;
  telefono?: string | null;
  direccion?: string | null;
  cuenta?: ICuenta | null;
  auditoria?: IAuditoria | null;
}

export class Persona implements IPersona {
  constructor(
    public id?: number,
    public nombre?: string | null,
    public apellidos?: string | null,
    public tipoDocumento?: string | null,
    public numeroDocumento?: string | null,
    public email?: string | null,
    public telefono?: string | null,
    public direccion?: string | null,
    public cuenta?: ICuenta | null,
    public auditoria?: IAuditoria | null
  ) {}
}

export function getPersonaIdentifier(persona: IPersona): number | undefined {
  return persona.id;
}
