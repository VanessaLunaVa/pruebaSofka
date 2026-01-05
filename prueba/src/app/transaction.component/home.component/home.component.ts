import { Component, inject, OnDestroy, OnInit } from '@angular/core';
import { CommonModule, DatePipe } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Transaction } from '../../interface/transaction.interface';
import { TransactionService } from '../../services/transaction.service';

@Component({
  selector: 'app-home.component',
  imports: [DatePipe, CommonModule, FormsModule],
  templateUrl: './home.component.html',
  styleUrl: './home.component.css',
})
export class HomeComponent implements OnInit, OnDestroy {
  
  private transactionService = inject(TransactionService);

  transactions = this.transactionService.transactions;
  isConnected = this.transactionService.isConnected;
  lastUpdate = this.transactionService.lastUpdate;

  ngOnInit() {
    this.transactionService.connectSSE();
  }

  ngOnDestroy() {
    console.log('Componente destruido - Cerrando SSE');
    this.transactionService.closeSSE();
  }

  createTestTransaction() {
    const formData: Transaction = {
      clientId: 1,
      valor: 10000,
      description: 'Prueba'
    };

    // Enviar al backend
    this.transactionService.create(formData)
      .subscribe({
        next: (response) => {
          console.log('Transacción creada:', response);
        },
        error: (error) => {
          console.error('Error:', error);
        }
      });
  }

  
}