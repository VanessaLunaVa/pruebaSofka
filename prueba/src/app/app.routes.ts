import { Routes } from '@angular/router';
import { TransactionComponent } from './transaction.component/transaction.component';
import { CreateComponent } from './transaction.component/create.component/create.component';
import { HomeComponent } from './transaction.component/home.component/home.component';

export const routes: Routes = [
    { path: 'page' , component: HomeComponent },
    { path: 'create' , component: CreateComponent },
    { path: 'list' , component: TransactionComponent },
    { path: '', pathMatch: 'full', redirectTo: 'page'}
];
