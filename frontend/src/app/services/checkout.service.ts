import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Purchase, PurchaseResponse } from '../models/purchase.model';
import { Country } from '../models/location.model';
import { Division } from '../models/location.model';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class CheckoutService {
  private apiUrl = environment.apiBaseUrl;

  constructor(private http: HttpClient) { }

  purchase(purchase: Purchase): Observable<PurchaseResponse> {
    return this.http.post<PurchaseResponse>(`${this.apiUrl}/checkout/purchase`, purchase);
  }

  getCountries(): Observable<Country[]> {
    return this.http.get<Country[]>(`${this.apiUrl}/countries`);
  }

  getDivisionsByCountry(countryId: number): Observable<Division[]> {
    return this.http.get<Division[]>(`${this.apiUrl}/divisions?countryId=${countryId}`);
  }
}
