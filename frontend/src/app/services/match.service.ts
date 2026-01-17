import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class MatchService {

  private baseUrl = 'http://localhost:8084'; // Spring Boot

  constructor(private http: HttpClient) {}

  getLiveMatches(): Observable<any[]> {
    return this.http.get<any[]>(`${this.baseUrl}/matches/live`);
  }

  getUpcomingMatches(): Observable<any[]> {
    return this.http.get<any[]>(`${this.baseUrl}/matches/upcoming`);
  }

  getCompletedMatches(): Observable<any[]> {
    return this.http.get<any[]>(`${this.baseUrl}/matches/completed`);
  }

  startMatch(matchId: number): Observable<any> {
    return this.http.post(`${this.baseUrl}/admin/matches/play/${matchId}`, {});
  }
}
