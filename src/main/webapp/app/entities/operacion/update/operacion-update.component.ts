import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IOperacion, Operacion } from '../operacion.model';
import { OperacionService } from '../service/operacion.service';
import { IAuditoria } from 'app/entities/auditoria/auditoria.model';
import { AuditoriaService } from 'app/entities/auditoria/service/auditoria.service';

@Component({
  selector: 'jhi-operacion-update',
  templateUrl: './operacion-update.component.html',
})
export class OperacionUpdateComponent implements OnInit {
  isSaving = false;

  auditoriasCollection: IAuditoria[] = [];

  editForm = this.fb.group({
    id: [],
    numeroOperacion: [],
    monto: [],
    cuentaOrigen: [],
    cuentaDestino: [],
    auditoria: [],
  });

  constructor(
    protected operacionService: OperacionService,
    protected auditoriaService: AuditoriaService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ operacion }) => {
      this.updateForm(operacion);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const operacion = this.createFromForm();
    if (operacion.id !== undefined) {
      this.subscribeToSaveResponse(this.operacionService.update(operacion));
    } else {
      this.subscribeToSaveResponse(this.operacionService.create(operacion));
    }
  }

  trackAuditoriaById(_index: number, item: IAuditoria): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IOperacion>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(operacion: IOperacion): void {
    this.editForm.patchValue({
      id: operacion.id,
      numeroOperacion: operacion.numeroOperacion,
      monto: operacion.monto,
      cuentaOrigen: operacion.cuentaOrigen,
      cuentaDestino: operacion.cuentaDestino,
      auditoria: operacion.auditoria,
    });

    this.auditoriasCollection = this.auditoriaService.addAuditoriaToCollectionIfMissing(this.auditoriasCollection, operacion.auditoria);
  }

  protected loadRelationshipsOptions(): void {
    this.auditoriaService
      .query({ filter: 'operacion-is-null' })
      .pipe(map((res: HttpResponse<IAuditoria[]>) => res.body ?? []))
      .pipe(
        map((auditorias: IAuditoria[]) =>
          this.auditoriaService.addAuditoriaToCollectionIfMissing(auditorias, this.editForm.get('auditoria')!.value)
        )
      )
      .subscribe((auditorias: IAuditoria[]) => (this.auditoriasCollection = auditorias));
  }

  protected createFromForm(): IOperacion {
    return {
      ...new Operacion(),
      id: this.editForm.get(['id'])!.value,
      numeroOperacion: this.editForm.get(['numeroOperacion'])!.value,
      monto: this.editForm.get(['monto'])!.value,
      cuentaOrigen: this.editForm.get(['cuentaOrigen'])!.value,
      cuentaDestino: this.editForm.get(['cuentaDestino'])!.value,
      auditoria: this.editForm.get(['auditoria'])!.value,
    };
  }
}
