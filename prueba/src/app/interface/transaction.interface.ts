export interface Transaction {
  id?: number;
  clientId: number;  
  monto?: number;
  commission?: number;
  total?: number;
  fecha?: Date;
  status?: string;
  valor?: number;
  description?: string;
}

