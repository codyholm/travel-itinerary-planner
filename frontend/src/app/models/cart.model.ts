import { Vacation } from './vacation.model';
import { Excursion } from './excursion.model';

export interface CartItem {
  vacation: Vacation;
  excursions: Excursion[];
}

export interface Cart {
  id?: number;
  package_price: number;
  party_size: number;
  status?: string;
  orderTrackingNumber?: string;
}
