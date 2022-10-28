import dayjs from 'dayjs/esm';

export interface IOperacion {
  id?: number;
  numeroOperacion?: string | null;
  monto?: number | null;
  cuentaOrigen?: number | null;
  cuentaDestino?: number | null;
  usuarioCreacion?: string | null;
  fechaCreacion?: dayjs.Dayjs | null;
  usuarioModificacion?: string | null;
  fechaModificacion?: dayjs.Dayjs | null;
}

export class Operacion implements IOperacion {
  constructor(
    public id?: number,
    public numeroOperacion?: string | null,
    public monto?: number | null,
    public cuentaOrigen?: number | null,
    public cuentaDestino?: number | null,
    public usuarioCreacion?: string | null,
    public fechaCreacion?: dayjs.Dayjs | null,
    public usuarioModificacion?: string | null,
    public fechaModificacion?: dayjs.Dayjs | null,
  ) {}
}

export function getOperacionIdentifier(operacion: IOperacion): number | undefined {
  return operacion.id;
}
