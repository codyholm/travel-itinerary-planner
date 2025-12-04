import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-vacation-list',
  standalone: true,
  imports: [CommonModule],
  template: `
    <div class="container py-4">
      <h1>Vacation Packages</h1>
      <p>Loading vacations...</p>
    </div>
  `
})
export class VacationListComponent {}
