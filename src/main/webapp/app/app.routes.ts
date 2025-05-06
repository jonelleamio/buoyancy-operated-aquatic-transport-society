import { Routes } from '@angular/router';
import {AppComponent} from './app.component';
import {AuthGuard} from './infrastructure/out/guards/auth.guard';
import {LoginComponent} from './infrastructure/out/ui/components/login.component';

export const routes: Routes = [
  { path: 'login', component: LoginComponent },
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
