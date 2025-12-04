import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-cart',
  standalone: true,
  imports: [CommonModule],
  template: `
    <div class="container py-4">
      <h1>Shopping Cart</h1>
      <p>Your cart is empty.</p>
    </div>
  `
})
export class CartComponent {}
