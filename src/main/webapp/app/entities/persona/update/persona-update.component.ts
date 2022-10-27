import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IPersona, Persona } from '../persona.model';
import { PersonaService } from '../service/persona.service';
import { ICuenta } from 'app/entities/cuenta/cuenta.model';
import { CuentaService } from 'app/entities/cuenta/service/cuenta.service';
import { IAuditoria } from 'app/entities/auditoria/auditoria.model';
import { AuditoriaService } from 'app/entities/auditoria/service/auditoria.service';

@Component({
  selector: 'jhi-persona-update',
  templateUrl: './persona-update.component.html',
})
export class PersonaUpdateComponent implements OnInit {
  isSaving = false;

  cuentasCollection: ICuenta[] = [];
  auditoriasCollection: IAuditoria[] = [];

  editForm = this.fb.group({
    id: [],
    nombre: [],
    apellidos: [],
    tipoDocumento: [],
    numeroDocumento: [],
    email: [],
    telefono: [],
    direccion: [],
    cuenta: [],
    auditoria: [],
  });

  constructor(
    protected personaService: PersonaService,
    protected cuentaService: CuentaService,
    protected auditoriaService: AuditoriaService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ persona }) => {
      this.updateForm(persona);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const persona = this.createFromForm();
    if (persona.id !== undefined) {
      this.subscribeToSaveResponse(this.personaService.update(persona));
    } else {
      this.subscribeToSaveResponse(this.personaService.create(persona));
    }
  }

  trackCuentaById(_index: number, item: ICuenta): number {
    return item.id!;
  }

  trackAuditoriaById(_index: number, item: IAuditoria): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPersona>>): void {
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

  protected updateForm(persona: IPersona): void {
    this.editForm.patchValue({
      id: persona.id,
      nombre: persona.nombre,
      apellidos: persona.apellidos,
      tipoDocumento: persona.tipoDocumento,
      numeroDocumento: persona.numeroDocumento,
      email: persona.email,
      telefono: persona.telefono,
      direccion: persona.direccion,
      cuenta: persona.cuenta,
      auditoria: persona.auditoria,
    });

    this.cuentasCollection = this.cuentaService.addCuentaToCollectionIfMissing(this.cuentasCollection, persona.cuenta);
    this.auditoriasCollection = this.auditoriaService.addAuditoriaToCollectionIfMissing(this.auditoriasCollection, persona.auditoria);
  }

  protected loadRelationshipsOptions(): void {
    this.cuentaService
      .query({ filter: 'persona-is-null' })
      .pipe(map((res: HttpResponse<ICuenta[]>) => res.body ?? []))
      .pipe(map((cuentas: ICuenta[]) => this.cuentaService.addCuentaToCollectionIfMissing(cuentas, this.editForm.get('cuenta')!.value)))
      .subscribe((cuentas: ICuenta[]) => (this.cuentasCollection = cuentas));

    this.auditoriaService
      .query({ filter: 'persona-is-null' })
      .pipe(map((res: HttpResponse<IAuditoria[]>) => res.body ?? []))
      .pipe(
        map((auditorias: IAuditoria[]) =>
          this.auditoriaService.addAuditoriaToCollectionIfMissing(auditorias, this.editForm.get('auditoria')!.value)
        )
      )
      .subscribe((auditorias: IAuditoria[]) => (this.auditoriasCollection = auditorias));
  }

  protected createFromForm(): IPersona {
    return {
      ...new Persona(),
      id: this.editForm.get(['id'])!.value,
      nombre: this.editForm.get(['nombre'])!.value,
      apellidos: this.editForm.get(['apellidos'])!.value,
      tipoDocumento: this.editForm.get(['tipoDocumento'])!.value,
      numeroDocumento: this.editForm.get(['numeroDocumento'])!.value,
      email: this.editForm.get(['email'])!.value,
      telefono: this.editForm.get(['telefono'])!.value,
      direccion: this.editForm.get(['direccion'])!.value,
      cuenta: this.editForm.get(['cuenta'])!.value,
      auditoria: this.editForm.get(['auditoria'])!.value,
    };
  }
}
