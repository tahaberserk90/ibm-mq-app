import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Partner } from '../models/partner.model';

@Injectable({
  providedIn: 'root'
})
export class PartnerService {
  private apiUrl = 'api/partners'; // Update with your actual API endpoint

  constructor(private http: HttpClient) { }

  getPartners(): Observable<Partner[]> {
    return this.http.get<Partner[]>(this.apiUrl);
  }

  addPartner(partner: Partner): Observable<Partner> {
    return this.http.post<Partner>(this.apiUrl, partner);
  }

  deletePartner(id: string): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}