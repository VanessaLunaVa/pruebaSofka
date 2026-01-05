import { Component, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Dashboard } from './dashboard/dashboard';

@Component({
  selector: 'app-root',
  imports: [
    CommonModule, FormsModule, Dashboard
  ],
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class App {
  protected readonly title = signal('prueba');

}
