import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-confirmation',
  standalone: true,
  imports: [CommonModule],
  template: `
    <div class="container py-4">
      <h1>Order Confirmed!</h1>
      <p>Thank you for your order.</p>
    </div>
  `
})
export class ConfirmationComponent {}
