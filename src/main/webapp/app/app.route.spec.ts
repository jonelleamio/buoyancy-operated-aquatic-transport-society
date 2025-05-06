import { TestBed } from '@angular/core/testing';
import { Router, provideRouter } from '@angular/router';
import {routes} from './app.routes';
import {beforeEach, it, expect} from 'vitest';

describe('AppRoutes', () => {
  let router: Router;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideRouter(routes)],
    }).compileComponents();
    router = TestBed.inject(Router);
    router.initialNavigation();
  });

  it('should be defined', () => {
    expect(routes).toBeDefined();
  });

  it('should navigate on login endpoint', () => {
    router.navigateByUrl('/login');
  });

  it('should not navigate on boats endpoint without token', () => {
    router.navigateByUrl('/boats');
  });
});
