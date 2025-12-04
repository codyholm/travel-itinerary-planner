import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-vacation-detail',
  standalone: true,
  imports: [CommonModule],
  template: `
    <div class="container py-4">
      <h1>Vacation Details</h1>
      <p>Loading vacation details...</p>
    </div>
  `
})
export class VacationDetailComponent {}
