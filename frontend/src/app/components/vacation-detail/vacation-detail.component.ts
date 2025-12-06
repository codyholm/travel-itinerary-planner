import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, Router } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { VacationService } from '../../services/vacation.service';
import { CartService } from '../../services/cart.service';
import { Vacation } from '../../models/vacation.model';
import { Excursion } from '../../models/excursion.model';

@Component({
  selector: 'app-vacation-detail',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './vacation-detail.component.html',
  styleUrl: './vacation-detail.component.scss'
})
export class VacationDetailComponent implements OnInit {
  vacation: Vacation | null = null;
  excursions: Excursion[] = [];
  selectedExcursions: Set<number> = new Set();
  loading = true;
  errorMessage = '';

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private vacationService: VacationService,
    private cartService: CartService
  ) {}

  ngOnInit(): void {
    const id = this.route.snapshot.paramMap.get('id');
    if (id) {
      this.loadVacationDetails(+id);
    } else {
      this.errorMessage = 'Invalid vacation ID';
      this.loading = false;
    }
  }

  loadVacationDetails(id: number): void {
    this.vacationService.getVacationById(id).subscribe({
      next: (vacation) => {
        this.vacation = vacation;
        this.loadExcursions(id);
      },
      error: (err) => {
        console.error('Error loading vacation:', err);
        this.errorMessage = 'Failed to load vacation details.';
        this.loading = false;
      }
    });
  }

  loadExcursions(vacationId: number): void {
    this.vacationService.getExcursionsByVacation(vacationId).subscribe({
      next: (excursions) => {
        this.excursions = excursions;
        this.loading = false;
      },
      error: (err) => {
        console.error('Error loading excursions:', err);
        this.loading = false;
      }
    });
  }

  toggleExcursion(excursionId: number): void {
    if (this.selectedExcursions.has(excursionId)) {
      this.selectedExcursions.delete(excursionId);
    } else {
      this.selectedExcursions.add(excursionId);
    }
  }

  isExcursionSelected(excursionId: number): boolean {
    return this.selectedExcursions.has(excursionId);
  }

  getTotal(): number {
    const vacationPrice = this.vacation?.travel_price || 0;
    const excursionsTotal = this.excursions
      .filter(exc => this.selectedExcursions.has(exc.id!))
      .reduce((sum, exc) => sum + (exc.excursion_price || 0), 0);
    return vacationPrice + excursionsTotal;
  }

  addToCart(): void {
    if (!this.vacation) return;

    const selectedExcursionObjects = this.excursions.filter(
      exc => this.selectedExcursions.has(exc.id!)
    );

    this.cartService.addToCart(this.vacation, selectedExcursionObjects);
    this.router.navigate(['/cart']);
  }
}
