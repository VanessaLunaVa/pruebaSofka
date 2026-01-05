import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { MatIconModule } from '@angular/material/icon';
import { MatListModule } from '@angular/material/list';
import { MatSidenavModule } from '@angular/material/sidenav';
import { RouterLink, RouterLinkActive } from '@angular/router';

@Component({
  selector: 'app-menu',
  imports: [MatListModule, MatSidenavModule, RouterLink, RouterLinkActive,
    CommonModule, FormsModule, MatIconModule],
  templateUrl: './menu.component.html',
  styleUrl: './menu.component.css',
})
export class MenuComponent {

    menuItems = [
    { label: 'Inicio', icon: 'home', route: '/' },
    { label: 'Transacciones', icon: 'list', route: '/list' },
    { label: 'Nueva Transacción', icon: 'add', route: '/create' }
  ];

}
