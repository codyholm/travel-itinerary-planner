import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';

@Component({
    selector: 'app-confirmation',
    imports: [CommonModule],
    templateUrl: './confirmation.component.html',
    styleUrl: './confirmation.component.scss'
})
export class ConfirmationComponent implements OnInit {
  orderTrackingNumber: string | null = null;

  constructor(private router: Router) {
    const navigation = this.router.getCurrentNavigation();
    if (navigation?.extras?.state) {
      this.orderTrackingNumber = navigation.extras.state['orderTrackingNumber'];
    }
  }

  ngOnInit(): void {
    // If no tracking number, redirect to home
    if (!this.orderTrackingNumber) {
      this.router.navigate(['/']);
    }
  }
}
