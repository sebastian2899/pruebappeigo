import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ICuenta } from '../cuenta.model';
import { CuentaService } from '../service/cuenta.service';
import { CuentaDeleteDialogComponent } from '../delete/cuenta-delete-dialog.component';

@Component({
  selector: 'jhi-cuenta',
  templateUrl: './cuenta.component.html',
})
export class CuentaComponent implements OnInit {
  cuentas?: ICuenta[];
  isLoading = false;

  constructor(protected cuentaService: CuentaService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.cuentaService.query().subscribe({
      next: (res: HttpResponse<ICuenta[]>) => {
        this.isLoading = false;
        this.cuentas = res.body ?? [];
      },
      error: () => {
        this.isLoading = false;
      },
    });
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(_index: number, item: ICuenta): number {
    return item.id!;
  }

  delete(cuenta: ICuenta): void {
    const modalRef = this.modalService.open(CuentaDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.cuenta = cuenta;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}