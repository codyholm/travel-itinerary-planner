import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { CartItem } from '../models/cart.model';
import { Vacation } from '../models/vacation.model';
import { Excursion } from '../models/excursion.model';

@Injectable({
  providedIn: 'root'
})
export class CartService {
  private cartItemsSubject = new BehaviorSubject<CartItem[]>([]);
  public cartItems$: Observable<CartItem[]> = this.cartItemsSubject.asObservable();

  constructor() {
    // Load cart from sessionStorage on init
    const savedCart = sessionStorage.getItem('cart');
    if (savedCart) {
      this.cartItemsSubject.next(JSON.parse(savedCart));
    }
  }

  addToCart(vacation: Vacation, excursions: Excursion[]): void {
    const currentItems = this.cartItemsSubject.value;
    
    // Check if vacation already in cart
    const existingIndex = currentItems.findIndex(item => item.vacation.id === vacation.id);
    
    if (existingIndex > -1) {
      // Update existing cart item
      currentItems[existingIndex] = { vacation, excursions };
    } else {
      // Add new cart item
      currentItems.push({ vacation, excursions });
    }
    
    this.updateCart(currentItems);
  }

  removeFromCart(vacationId: number): void {
    const currentItems = this.cartItemsSubject.value;
    const updatedItems = currentItems.filter(item => item.vacation.id !== vacationId);
    this.updateCart(updatedItems);
  }

  clearCart(): void {
    this.updateCart([]);
  }

  getCartTotal(): number {
    return this.cartItemsSubject.value.reduce((total, item) => {
      const vacationPrice = item.vacation.travel_price || 0;
      const excursionsTotal = item.excursions.reduce((sum, exc) => sum + (exc.excursion_price || 0), 0);
      return total + vacationPrice + excursionsTotal;
    }, 0);
  }

  getCartItemCount(): number {
    return this.cartItemsSubject.value.length;
  }

  private updateCart(items: CartItem[]): void {
    this.cartItemsSubject.next(items);
    sessionStorage.setItem('cart', JSON.stringify(items));
  }
}
