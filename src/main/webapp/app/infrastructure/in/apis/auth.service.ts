import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import {tap, Observable, map} from 'rxjs';

interface LoginReq { username: string; password: string; }
interface LoginRes { token: string; }

@Injectable({ providedIn: 'root' })
export class AuthService {
  private tokenKey = 'jwTokenKey';

  constructor(private http: HttpClient) {}

  login({username, password}:LoginReq): Observable<void> {
    return this.http
      .post<LoginRes>('/api/auth/login', { username, password })
      .pipe(
        tap(res => localStorage.setItem(this.tokenKey, res.token)),
        map(() => void 0)
      );
  }

  getToken(): string | null {
    return localStorage.getItem(this.tokenKey);
  }

  logout(): void {
    localStorage.removeItem(this.tokenKey);
  }

  isAuthenticated(): boolean  {
    return !!this.getToken();
  }
}
