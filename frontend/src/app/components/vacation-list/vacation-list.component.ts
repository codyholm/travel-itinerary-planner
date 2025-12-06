import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { VacationService } from '../../services/vacation.service';
import { Vacation } from '../../models/vacation.model';

@Component({
  selector: 'app-vacation-list',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './vacation-list.component.html',
  styleUrl: './vacation-list.component.scss'
})
export class VacationListComponent implements OnInit {
  vacations: Vacation[] = [];
  loading = true;
  errorMessage = '';

  constructor(private vacationService: VacationService) {}

  ngOnInit(): void {
    this.loadVacations();
  }

  loadVacations(): void {
    this.vacationService.getVacations().subscribe({
      next: (data) => {
        this.vacations = data;
        this.loading = false;
      },
      error: (err) => {
        console.error('Error loading vacations:', err);
        this.errorMessage = 'Failed to load vacation packages. Please ensure the backend is running.';
        this.loading = false;
      }
    });
  }
}
