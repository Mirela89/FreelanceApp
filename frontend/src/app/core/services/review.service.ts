import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Review, ReviewRequest } from '../../models/review.model';

@Injectable({ providedIn: 'root' })
export class ReviewService {
  private apiUrl = 'http://localhost:8081/api/contracts';

  constructor(private http: HttpClient) {}

  add(contractId: number, request: ReviewRequest): Observable<Review> {
    return this.http.post<Review>(`${this.apiUrl}/${contractId}/reviews`, request);
  }

  getByContract(contractId: number): Observable<Review[]> {
    return this.http.get<Review[]>(`${this.apiUrl}/${contractId}/reviews`);
  }
}
