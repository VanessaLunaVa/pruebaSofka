import { CommonModule } from '@angular/common';
import { Component, Signal, signal } from '@angular/core';
import { FormGroup, FormControl, Validators, AbstractControl, ReactiveFormsModule, ValidationErrors } from '@angular/forms';
import { Transaction } from '../../interface/transaction.interface';
import { MatIcon } from '@angular/material/icon';
import { TransactionService } from '../../services/transaction.service';

@Component({
  selector: 'app-create.component',
  imports: [CommonModule, ReactiveFormsModule, MatIcon],
  templateUrl: './create.component.html',
  styleUrl: './create.component.css',
})
export class CreateComponent {

  transactionForm!: FormGroup;
  isSubmitting = signal(false);
  submitError = signal<string | null>(null);
  submitSuccess = signal<string | null>(null);


  transactionClient = [
    { value: 1, label: 'Vanessa' },
    { value: 2, label: 'Juan' },
    { value: 3, label: 'Ramiro' },
    { value: 4, label: 'Karen' }
  ];

  constructor(
    private transactionService: TransactionService
  ) {}

  ngOnInit() {
    this.initForm();
  }

  initForm() {
    this.transactionForm = new FormGroup<{
    client: FormControl<any>;
    amount: FormControl<number>;
    description: FormControl<string>;
  }>({
    client: new FormControl<any>(null, {
      nonNullable: true, validators: [Validators.required, Validators.maxLength(20)]
    }),
    amount: new FormControl(0, {
      nonNullable: true, validators: [Validators.required,
      Validators.min(1),
      Validators.max(1000000),
      this.decimalValidator]
    }),
    description: new FormControl('', {
      nonNullable: true, validators: [Validators.required, Validators.maxLength(255)]
    })
  });
  }

  // Validador personalizado para decimales
  decimalValidator(control: AbstractControl): ValidationErrors | null {
    const value = control.value;
    if (!value) return null;

    const regex = /^\d+(\.\d{1,2})?$/;
    if (!regex.test(value)) {
      return { invalidDecimal: true };
    }
    return null;
  }

  // Verificar si un campo es inválido y ha sido tocado
  isFieldInvalid(fieldName: string): boolean {
    const field = this.transactionForm.get(fieldName);
    return !!(field && field.invalid && (field.touched || field.dirty));
  }

  // Obtener mensaje de error para un campo
  getErrorMessage(fieldName: string): string {
    const field = this.transactionForm.get(fieldName);
    if (!field || !field.errors) return '';

    if (field.errors['required']) return 'Este campo es requerido';
    if (field.errors['min']) return `Valor mínimo: ${field.errors['min'].min}`;
    if (field.errors['max']) return `Valor máximo: ${field.errors['max'].max}`;
    if (field.errors['minlength']) return `Mínimo ${field.errors['minlength'].requiredLength} caracteres`;
    if (field.errors['maxlength']) return `Máximo ${field.errors['maxlength'].requiredLength} caracteres`;
    if (field.errors['pattern']) return 'Formato inválido (solo letras, números, guión y guión bajo)';
    if (field.errors['invalidDecimal']) return 'Debe ser un número con máximo 2 decimales';

    return 'Campo inválido';
  }

  // Enviar formulario
  onSubmit() {
    // Marcar todos los campos como touched para mostrar errores
    Object.keys(this.transactionForm.controls).forEach(key => {
      this.transactionForm.get(key)?.markAsTouched();
    });

    // Validar formulario
    if (this.transactionForm.invalid) {
      this.submitError.set('Por favor corrija los errores en el formulario');
      return;
    }

    this.isSubmitting.set(true);
    this.submitError.set(null);
    this.submitSuccess.set(null);

    const formData: Transaction = {
        clientId: this.transactionForm.value.client,
        valor: this.transactionForm.value.amount,
        description: this.transactionForm.value.description
    };

    // Enviar al backend
    this.transactionService.create(formData)
      .subscribe({
        next: (response) => {
          console.log('Transacción creada:', response);
          this.submitSuccess.set('Transacción creada exitosamente');
          // Reset form después de 2 segundos
          setTimeout(() => {
            this.transactionForm.reset({ currency: 'USD' });
            this.submitSuccess.set(null);
          }, 2000);

          this.isSubmitting.set(false);
        },
        error: (error) => {
          console.error('Error:', error);
          this.submitError = error.error?.message || 'Error al crear la transacción';
          this.isSubmitting.set(false);
        }
      });
  }

  // Limpiar formulario
  onReset() {
    this.submitError.set(null);
    this.submitSuccess.set(null);
  }

}
