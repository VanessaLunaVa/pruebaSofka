import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { MatCardModule } from '@angular/material/card';
import { MatIconModule } from '@angular/material/icon';
import { MatListModule } from '@angular/material/list';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatToolbarModule } from '@angular/material/toolbar';
import { TransactionComponent } from '../transaction.component/transaction.component';
import { Footer } from '../footer/footer';
import { HeaderComponent } from '../header.component/header.component';
import { RouterOutlet } from '@angular/router';
import { MenuComponent } from '../menu.component/menu.component';

@Component({
  selector: 'app-dashboard',
  imports: [ CommonModule, FormsModule, MatToolbarModule, MatListModule,
    MatSidenavModule, Footer, HeaderComponent, RouterOutlet, MenuComponent,    
    MatCardModule],
  templateUrl: './dashboard.html',
  styleUrl: './dashboard.css',
})
export class Dashboard {



}
