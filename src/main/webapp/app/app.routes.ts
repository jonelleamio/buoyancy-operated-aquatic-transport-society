import { Routes } from '@angular/router';
import {AppComponent} from './app.component';
import {AuthGuard} from './infrastructure/out/guards/auth.guard';

export const routes: Routes = [
  { path: 'login', component: AppComponent },
  {
    path: 'boats',
    canActivate: [AuthGuard],
    children: [
      { path: '',       component: AppComponent },
      { path: 'new',    component: AppComponent },
      { path: ':id',    component: AppComponent },
      { path: ':id/edit', component: AppComponent },
    ],
  },
  { path: '**', redirectTo: 'boats' },
];
