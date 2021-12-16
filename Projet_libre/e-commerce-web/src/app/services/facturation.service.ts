import { Injectable } from '@angular/core';
import { HttpHeaders, HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Facturation } from '../model/facturation-model';

const httpOptions = {
   headers: new HttpHeaders({ 'Content-Type': 'application/json', 'Access-Control-Allow-Origin': '*', 'Access-Control-Allow-Methods': '*'})
};

@Injectable({
  providedIn: 'root'
})

export class FacturationService {

  private facturationList = 'http://localhost:8090/facturation';

  constructor(private http: HttpClient) { }

  getFacturationBoard(): Observable<Facturation[]> {
    return this.http.get<Facturation[]>(this.facturationList, httpOptions);
  }

  getFacturationById(id: number): Observable<Facturation> {
    return this.http.get<Facturation>(this.facturationList + '/' + id, httpOptions);
  }

  createFacturation(facturation: Facturation): Observable<Facturation> {
    return this.http.post<Facturation>(this.facturationList, facturation, httpOptions);
  }

  updateFacturation(facturation: Facturation): Observable<Facturation> {
    return this.http.put<Facturation>(this.facturationList + '/' + facturation.id, facturation, httpOptions);
  }

  deleteFacturation(id: number): Observable<any> {
    return this.http.delete<any>(this.facturationList + '/' + id, httpOptions);
  }

  deleteFacturationByUser(id: number): Observable<any> {
    return this.http.delete<any>(this.facturationList + '/deleteByUser/' + id, httpOptions);
  }
}
