import { Customer } from './customer.model';
import { Cart, CartItem } from './cart.model';

export interface Purchase {
  customer: Customer;
  cart: Cart;
  cartItems: PurchaseCartItem[];
}

// Backend expects cart items in this format
export interface PurchaseCartItem {
  vacation: { id: number };
  excursions: { id: number }[];
}

export interface PurchaseResponse {
  success: boolean;
  orderTrackingNumber: string | null;
  errorMessage: string | null;
}
