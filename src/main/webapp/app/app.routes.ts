import { Routes } from '@angular/router';
import {AuthGuard} from './infrastructure/out/guards/auth.guard';
import {LoginComponent} from './infrastructure/out/ui/components/login/login.component';
import {BrowseBoatsComponent} from './infrastructure/out/ui/components/browse-boats/browse-boats.component';
import {FormBoatComponent} from './infrastructure/out/ui/components/form-boat/form-boat.component';
import {DetailBoatComponent} from './infrastructure/out/ui/components/detail-boat/detail-boat.component';

export const routes: Routes = [
  { path: 'login', component: LoginComponent },
  {
    path: 'boats',
    canActivate: [AuthGuard],
    children: [
      { path: '',       component: BrowseBoatsComponent },
      { path: 'new',    component: FormBoatComponent },
      { path: ':id',    component: DetailBoatComponent },
      { path: ':id/edit', component: FormBoatComponent },
    ],
  },
  { path: '**', redirectTo: 'boats' },
];
