import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { PersonaService } from '../service/persona.service';
import { IPersona, Persona } from '../persona.model';
import { ICuenta } from 'app/entities/cuenta/cuenta.model';
import { CuentaService } from 'app/entities/cuenta/service/cuenta.service';
import { IAuditoria } from 'app/entities/auditoria/auditoria.model';
import { AuditoriaService } from 'app/entities/auditoria/service/auditoria.service';

import { PersonaUpdateComponent } from './persona-update.component';

describe('Persona Management Update Component', () => {
  let comp: PersonaUpdateComponent;
  let fixture: ComponentFixture<PersonaUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let personaService: PersonaService;
  let cuentaService: CuentaService;
  let auditoriaService: AuditoriaService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [PersonaUpdateComponent],
      providers: [
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(PersonaUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(PersonaUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    personaService = TestBed.inject(PersonaService);
    cuentaService = TestBed.inject(CuentaService);
    auditoriaService = TestBed.inject(AuditoriaService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call cuenta query and add missing value', () => {
      const persona: IPersona = { id: 456 };
      const cuenta: ICuenta = { id: 10971 };
      persona.cuenta = cuenta;

      const cuentaCollection: ICuenta[] = [{ id: 42311 }];
      jest.spyOn(cuentaService, 'query').mockReturnValue(of(new HttpResponse({ body: cuentaCollection })));
      const expectedCollection: ICuenta[] = [cuenta, ...cuentaCollection];
      jest.spyOn(cuentaService, 'addCuentaToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ persona });
      comp.ngOnInit();

      expect(cuentaService.query).toHaveBeenCalled();
      expect(cuentaService.addCuentaToCollectionIfMissing).toHaveBeenCalledWith(cuentaCollection, cuenta);
      expect(comp.cuentasCollection).toEqual(expectedCollection);
    });

    it('Should call auditoria query and add missing value', () => {
      const persona: IPersona = { id: 456 };
      const auditoria: IAuditoria = { id: 66336 };
      persona.auditoria = auditoria;

      const auditoriaCollection: IAuditoria[] = [{ id: 20600 }];
      jest.spyOn(auditoriaService, 'query').mockReturnValue(of(new HttpResponse({ body: auditoriaCollection })));
      const expectedCollection: IAuditoria[] = [auditoria, ...auditoriaCollection];
      jest.spyOn(auditoriaService, 'addAuditoriaToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ persona });
      comp.ngOnInit();

      expect(auditoriaService.query).toHaveBeenCalled();
      expect(auditoriaService.addAuditoriaToCollectionIfMissing).toHaveBeenCalledWith(auditoriaCollection, auditoria);
      expect(comp.auditoriasCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const persona: IPersona = { id: 456 };
      const cuenta: ICuenta = { id: 4021 };
      persona.cuenta = cuenta;
      const auditoria: IAuditoria = { id: 17647 };
      persona.auditoria = auditoria;

      activatedRoute.data = of({ persona });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(persona));
      expect(comp.cuentasCollection).toContain(cuenta);
      expect(comp.auditoriasCollection).toContain(auditoria);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Persona>>();
      const persona = { id: 123 };
      jest.spyOn(personaService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ persona });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: persona }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(personaService.update).toHaveBeenCalledWith(persona);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Persona>>();
      const persona = new Persona();
      jest.spyOn(personaService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ persona });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: persona }));
      saveSubject.complete();

      // THEN
      expect(personaService.create).toHaveBeenCalledWith(persona);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Persona>>();
      const persona = { id: 123 };
      jest.spyOn(personaService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ persona });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(personaService.update).toHaveBeenCalledWith(persona);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackCuentaById', () => {
      it('Should return tracked Cuenta primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackCuentaById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackAuditoriaById', () => {
      it('Should return tracked Auditoria primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackAuditoriaById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
