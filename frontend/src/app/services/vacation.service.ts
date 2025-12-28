import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { Vacation } from '../models/vacation.model';
import { Excursion } from '../models/excursion.model';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class VacationService {
  private apiUrl = environment.apiBaseUrl;

  constructor(private http: HttpClient) { }

  getVacations(): Observable<Vacation[]> {
    return this.http.get<{ _embedded: { vacations: Vacation[] } }>(`${this.apiUrl}/vacations`)
      .pipe(
        map(response => response._embedded.vacations)
      );
  }

  getVacationById(id: number): Observable<Vacation> {
    return this.http.get<Vacation>(`${this.apiUrl}/vacations/${id}`);
  }

  getExcursionsByVacation(vacationId: number): Observable<Excursion[]> {
    return this.http.get<{ _embedded: { excursions: Excursion[] } }>(
      `${this.apiUrl}/excursions/search/findByVacationId?vacationId=${vacationId}`
    ).pipe(
      map(response => response._embedded.excursions)
    );
  }
}
