import { Division } from './location.model';

export interface Customer {
  id?: number;
  firstName: string;
  lastName: string;
  address: string;
  postal_code: string;
  phone: string;
  division?: Division;
}
