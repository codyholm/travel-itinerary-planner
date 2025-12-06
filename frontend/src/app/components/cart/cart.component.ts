import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { CartService } from '../../services/cart.service';
import { CartItem } from '../../models/cart.model';

@Component({
  selector: 'app-cart',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './cart.component.html',
  styleUrl: './cart.component.scss'
})
export class CartComponent implements OnInit {
  cartItems: CartItem[] = [];
  total = 0;

  constructor(
    private cartService: CartService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.cartService.cartItems$.subscribe(items => {
      this.cartItems = items;
      this.total = this.cartService.getCartTotal();
    });
  }

  getVacationTotal(item: CartItem): number {
    return (item.vacation.travel_price || 0);
  }

  getExcursionsTotal(item: CartItem): number {
    return item.excursions.reduce((sum, exc) => sum + (exc.excursion_price || 0), 0);
  }

  getItemTotal(item: CartItem): number {
    return this.getVacationTotal(item) + this.getExcursionsTotal(item);
  }

  removeItem(vacationId: number): void {
    if (confirm('Remove this vacation from your cart?')) {
      this.cartService.removeFromCart(vacationId);
    }
  }

  proceedToCheckout(): void {
    this.router.navigate(['/checkout']);
  }

  continueShopping(): void {
    this.router.navigate(['/']);
  }
}
