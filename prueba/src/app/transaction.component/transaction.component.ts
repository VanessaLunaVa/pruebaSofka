import { ChangeDetectorRef, Component, computed, OnDestroy, OnInit, signal } from '@angular/core';
import { Subscription } from 'rxjs';
import { Transaction } from '../interface/transaction.interface';
import { TransactionService } from '../services/transaction.service';
import { CommonModule, DatePipe } from '@angular/common';
import { FormsModule } from '@angular/forms';

type SortKey = keyof Transaction | null;
type SortDirection = 'asc' | 'desc';

@Component({
  selector: 'app-transaction',
  imports: [CommonModule, FormsModule],
  templateUrl: './transaction.component.html',
  styleUrl: './transaction.component.css',
})
export class TransactionComponent implements OnInit {

  constructor(
    private transactionService: TransactionService
  ) { }

  ngOnInit() {
    this.getAll();
  }

  transactions = signal<Transaction[]>([]);


  totals = computed(() => {
    const trxs = this.transactions();
    return {
      monto: trxs.reduce((sum, trx) => sum + (trx.monto ?? 0), 0),
      commission: trxs.reduce((sum, trx) => sum + (trx.commission ?? 0), 0),
      total: trxs.reduce((sum, trx) => sum + (trx.total ?? 0), 0),
      count: trxs.length
    };
  });

  formatCurrency(amount: any): string {
    return new Intl.NumberFormat('es-CO', {
      style: 'currency',
      currency: 'COP',
      minimumFractionDigits: 2
    }).format(amount);
  }

  getAll() {
    this.transactionService.getAll().subscribe({
      next: (resp) => {
        this.transactions.set(resp.data);    
      },
      error: (err) => {
        console.error('Error al cargar datos:', err);
      }
    })
  }

}