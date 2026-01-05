import { Injectable, signal } from '@angular/core';
import { Observable } from 'rxjs';
import { Transaction } from '../interface/transaction.interface';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../environments/environment';
import { GeneralResponse } from '../interface/general-response.dto';

@Injectable({
  providedIn: 'root',
})
export class TransactionService  {

  constructor(
    private http: HttpClient
  ) { }

  private eventSource: EventSource | null = null;
  private reconnectAttempts = 0;
  private maxReconnectAttempts = 5;
  private reconnectTimeout: any = null;

  // Signals públicos
  transactions = signal<Transaction[]>([]);
  isConnected = signal<boolean>(false);
  lastUpdate = signal<Date>(new Date());

  connectSSE() {
    this.closeSSE();

    console.log('Conectando a SSE...');
    this.eventSource = new EventSource(`${environment.APIEndPointFlux}transactions/stream`);

    this.eventSource.onopen = () => {
      console.log('Conexion SSE establecida');
      this.isConnected.set(true);
      this.reconnectAttempts = 0;
    };

    this.eventSource.onmessage = (event) => {
      try {
        const transaction: Transaction = JSON.parse(event.data);
        console.log('Transaccion recibida:', transaction);
        
        this.transactions.update(trxs => [transaction, ...trxs]);
        this.lastUpdate.set(new Date());
      } catch (error) {
        console.error('Error parsing transaction:', error);
      }
    };

    this.eventSource.onerror = (error) => {
      console.error(' SSE error:', error);
      console.log('ReadyState:', this.eventSource?.readyState);
      
      this.isConnected.set(false);

      if (this.eventSource?.readyState === EventSource.CLOSED) {
        this.handleReconnect();
      }
    };
  }

  private handleReconnect() {
    if (this.reconnectAttempts >= this.maxReconnectAttempts) {
      console.error(' Maximo de intentos de reconexion alcanzado');
      this.isConnected.set(false);
      return;
    }

    this.reconnectAttempts++;
    const delay = Math.min(1000 * Math.pow(2, this.reconnectAttempts), 30000);
    
    console.log(` Reintentando conexion en ${delay}ms (intento ${this.reconnectAttempts}/${this.maxReconnectAttempts})`);

    this.reconnectTimeout = setTimeout(() => {
      this.connectSSE();
    }, delay);
  }

  closeSSE() {
    if (this.reconnectTimeout) {
      clearTimeout(this.reconnectTimeout);
      this.reconnectTimeout = null;
    }

    if (this.eventSource) {
      console.log(' Cerrando conexion SSE');
      this.eventSource.close();
      this.eventSource = null;
      this.isConnected.set(false);
    }

    this.reconnectAttempts = 0;
  }

  // Limpiar todas las transacciones
  clearTransactions() {
    this.transactions.set([]);
  }

  create(data: Transaction): Observable<GeneralResponse<Transaction>> {
    let path = `${environment.APIEndPointFlux}${'transactions'}`;
    return this.http.post<GeneralResponse<Transaction>>(
      path,
      data
    );
  }

  getAll(): Observable<GeneralResponse<Transaction[]>> {
    let path = `${environment.APIEndPointFlux}${'transactions'}`;
    return this.http.get<GeneralResponse<Transaction[]>>(path);
  }

}
