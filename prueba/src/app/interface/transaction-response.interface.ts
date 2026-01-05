export interface TransactionResponse {
  id: number;
  clienteId: number;  
  monto: string;
  commission: number;
  total: number;
  fecha: Date;
}