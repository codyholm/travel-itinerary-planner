import { Division } from './location.model';

export interface Customer {
  id?: number;
  firstName: string;
  lastName: string;
  email: string;
  address: string;
  city: string;
  postal_code: string;
  phone: string;
  division?: Division;
}
