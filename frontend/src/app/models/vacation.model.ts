import { Excursion } from './excursion.model';

export interface Vacation {
  id: number;
  vacation_title: string;
  description: string;
  travel_price: number;
  image_URL: string;
  create_date?: string;
  last_update?: string;
  excursions?: Excursion[];
  excursions_count?: number;
}
