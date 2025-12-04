import { Routes } from '@angular/router';

export const routes: Routes = [
  {
    path: '',
    loadComponent: () => import('./components/vacation-list/vacation-list.component')
      .then(m => m.VacationListComponent)
  },
  {
    path: 'vacations/:id',
    loadComponent: () => import('./components/vacation-detail/vacation-detail.component')
      .then(m => m.VacationDetailComponent)
  },
  {
    path: 'cart',
    loadComponent: () => import('./components/cart/cart.component')
      .then(m => m.CartComponent)
  },
  {
    path: 'checkout',
    loadComponent: () => import('./components/checkout/checkout.component')
      .then(m => m.CheckoutComponent)
  },
  {
    path: 'confirmation',
    loadComponent: () => import('./components/confirmation/confirmation.component')
      .then(m => m.ConfirmationComponent)
  },
  {
    path: '**',
    redirectTo: ''
  }
];
