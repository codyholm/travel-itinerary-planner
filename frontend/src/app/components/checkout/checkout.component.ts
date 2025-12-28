import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { CartService } from '../../services/cart.service';
import { CheckoutService } from '../../services/checkout.service';
import { Country, Division } from '../../models/location.model';
import { Purchase } from '../../models/purchase.model';
import { CartItem } from '../../models/cart.model';

@Component({
    selector: 'app-checkout',
    imports: [CommonModule, ReactiveFormsModule],
    templateUrl: './checkout.component.html',
    styleUrl: './checkout.component.scss'
})
export class CheckoutComponent implements OnInit {
  checkoutForm!: FormGroup;
  cartItems: CartItem[] = [];
  countries: Country[] = [];
  divisions: Division[] = [];
  total = 0;
  submitting = false;
  errorMessage = '';

  constructor(
    private fb: FormBuilder,
    private cartService: CartService,
    private checkoutService: CheckoutService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.initializeForm();
    this.loadCartData();
    this.loadCountries();
  }

  initializeForm(): void {
    this.checkoutForm = this.fb.group({
      firstName: ['', [Validators.required, Validators.minLength(2)]],
      lastName: ['', [Validators.required, Validators.minLength(2)]],
      address: ['', [Validators.required, Validators.minLength(5)]],
      city: ['', [Validators.required]],
      postal_code: ['', [Validators.required]],
      phone: ['', [Validators.required, Validators.pattern(/^\d{10,15}$/)]],
      country: ['', [Validators.required]],
      division: ['', [Validators.required]]
    });

    // Watch for country changes to load divisions
    this.checkoutForm.get('country')?.valueChanges.subscribe(countryId => {
      if (countryId) {
        this.loadDivisions(+countryId);
        this.checkoutForm.patchValue({ division: '' });
      } else {
        this.divisions = [];
      }
    });
  }

  loadCartData(): void {
    this.cartService.cartItems$.subscribe(items => {
      this.cartItems = items;
      this.total = this.cartService.getCartTotal();
      
      if (items.length === 0) {
        this.router.navigate(['/cart']);
      }
    });
  }

  loadCountries(): void {
    this.checkoutService.getCountries().subscribe({
      next: (countries) => {
        this.countries = countries;
      },
      error: (err) => {
        console.error('Error loading countries:', err);
        this.errorMessage = 'Failed to load countries. Please try again.';
      }
    });
  }

  loadDivisions(countryId: number): void {
    this.checkoutService.getDivisionsByCountry(countryId).subscribe({
      next: (divisions) => {
        this.divisions = divisions;
      },
      error: (err) => {
        console.error('Error loading divisions:', err);
      }
    });
  }

  onSubmit(): void {
    if (this.checkoutForm.invalid) {
      this.checkoutForm.markAllAsTouched();
      this.errorMessage = 'Please fill in all required fields correctly.';
      return;
    }

    if (this.cartItems.length === 0) {
      this.errorMessage = 'Your cart is empty.';
      return;
    }

    this.submitting = true;
    this.errorMessage = '';

    const formValue = this.checkoutForm.value;
    
    // Build Purchase object
    const purchase: Purchase = {
      customer: {
        firstName: formValue.firstName,
        lastName: formValue.lastName,
        address: formValue.address,
        postal_code: formValue.postal_code,
        phone: formValue.phone,
        division: {
          id: +formValue.division,
          division_name: '',
          country_id: +formValue.country
        }
      },
      cart: {
        package_price: this.total,
        party_size: 1,
        status: 'ordered'
      },
      cartItems: this.cartItems.map(item => ({
        vacation: { id: item.vacation.id! },
        excursions: item.excursions.map(exc => ({ id: exc.id! }))
      }))
    };

    this.checkoutService.purchase(purchase).subscribe({
      next: (response) => {
        if (response.success && response.orderTrackingNumber) {
          this.cartService.clearCart();
          this.router.navigate(['/confirmation'], { 
            state: { orderTrackingNumber: response.orderTrackingNumber } 
          });
        } else {
          this.errorMessage = response.errorMessage || 'An error occurred during checkout.';
          this.submitting = false;
        }
      },
      error: (err) => {
        console.error('Checkout error:', err);
        this.errorMessage = err.error?.errorMessage || 'Failed to process your order. Please try again.';
        this.submitting = false;
      }
    });
  }

  isFieldInvalid(fieldName: string): boolean {
    const field = this.checkoutForm.get(fieldName);
    return !!(field && field.invalid && (field.dirty || field.touched));
  }
}
