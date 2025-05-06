import { Injectable } from '@angular/core';
import { HttpClient }  from '@angular/common/http';
import { Observable, map } from 'rxjs';
import {BoatResource} from '../models/boat-resource.model';
import {environment} from '../../../../environments/environment';

interface Page<T> {
  _embedded: Record<string, T[]>;
  _links: Record<string, any>;
}

@Injectable({ providedIn: 'root' })
export class BoatService {
  private readonly BASE = `${environment.apiUrl}/api/boats`;

  constructor(private http: HttpClient) {}

  browseAllBoats(): Observable<BoatResource[]> {
    return this.http.get<Page<BoatResource>>(this.BASE).pipe(
      map(page => {
        const embedded = page._embedded;
        const firstKey = Object.keys(embedded)[0];
        return embedded[firstKey];
      })
    );
  }

  viewBoatDetails(id: string): Observable<BoatResource> {
    return this.http.get<BoatResource>(`${this.BASE}/${id}`);
  }

  registerBoat(boat: Partial<BoatResource>): Observable<BoatResource> {
    return this.http.post<BoatResource>(this.BASE, boat);
  }

  updateBoat(boat: BoatResource): Observable<BoatResource> {
    return this.http.put<BoatResource>(this.BASE, boat);
  }

  undockBoat(id: string): Observable<void> {
    return this.http.delete<void>(`${this.BASE}/${id}`);
  }
}
