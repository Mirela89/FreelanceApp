import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Application, ApplicationRequest } from '../../models/application.model';

@Injectable({ providedIn: 'root' })
export class ApplicationService {
  private apiUrl = 'http://localhost:8081/api';

  constructor(private http: HttpClient) {}

  apply(projectId: number, request: ApplicationRequest): Observable<Application> {
    return this.http.post<Application>(`${this.apiUrl}/projects/${projectId}/apply`, request);
  }

  getByProject(projectId: number): Observable<Application[]> {
    return this.http.get<Application[]>(`${this.apiUrl}/projects/${projectId}/applications`);
  }

  getMy(): Observable<Application[]> {
    return this.http.get<Application[]>(`${this.apiUrl}/applications/my`);
  }

  accept(id: number): Observable<Application> {
    return this.http.patch<Application>(`${this.apiUrl}/applications/${id}/accept`, {});
  }

  reject(id: number): Observable<Application> {
    return this.http.patch<Application>(`${this.apiUrl}/applications/${id}/reject`, {});
  }
}
