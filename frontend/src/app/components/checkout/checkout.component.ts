import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-checkout',
  standalone: true,
  imports: [CommonModule],
  template: `
    <div class="container py-4">
      <h1>Checkout</h1>
      <p>Checkout form coming soon...</p>
    </div>
  `
})
export class CheckoutComponent {}
