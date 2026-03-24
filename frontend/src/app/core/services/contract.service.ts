import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Contract } from '../../models/contract.model';

@Injectable({ providedIn: 'root' })
export class ContractService {
  private apiUrl = 'http://localhost:8081/api/contracts';

  constructor(private http: HttpClient) {}

  createFromApplication(applicationId: number): Observable<Contract> {
    return this.http.post<Contract>(`${this.apiUrl}/from-application/${applicationId}`, {});
  }

  getByProject(projectId: number): Observable<Contract> {
    return this.http.get<Contract>(`${this.apiUrl}/project/${projectId}`);
  }

  getMy(): Observable<Contract[]> {
    return this.http.get<Contract[]>(`${this.apiUrl}/my`);
  }

  complete(id: number): Observable<Contract> {
    return this.http.patch<Contract>(`${this.apiUrl}/${id}/complete`, {});
  }

  cancel(id: number): Observable<Contract> {
    return this.http.patch<Contract>(`${this.apiUrl}/${id}/cancel`, {});
  }
}
