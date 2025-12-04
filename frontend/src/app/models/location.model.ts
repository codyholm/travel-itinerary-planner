export interface Country {
  id: number;
  country_name: string;
}

export interface Division {
  id: number;
  division_name: string;
  country_id: number;
}
