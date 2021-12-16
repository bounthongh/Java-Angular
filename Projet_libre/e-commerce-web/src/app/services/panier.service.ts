import { Injectable } from '@angular/core';
import { HttpHeaders, HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Panier } from '../model/panier-model';

const httpOptions = {
   headers: new HttpHeaders({ 'Content-Type': 'application/json', 'Access-Control-Allow-Origin': '*', 'Access-Control-Allow-Methods': '*'})
};

@Injectable({
  providedIn: 'root'
})

export class PanierService {

  private panierList = 'http://localhost:8090/panier';

  constructor(private http: HttpClient) { }

  getPanierBoard(): Observable<Panier[]> {
    return this.http.get<Panier[]>(this.panierList, httpOptions);
  }

  getPanierById(id: number): Observable<Panier> {
    return this.http.get<Panier>(this.panierList + '/' + id, httpOptions);
  }

  createPanier(panier: Panier): Observable<Panier> {
    return this.http.post<Panier>(this.panierList, panier, httpOptions);
  }

  updatePanier(panier: Panier): Observable<Panier> {
    return this.http.put<Panier>(this.panierList + '/' + panier.id, panier, httpOptions);
  }

  deletePanier(id: number): Observable<any> {
    return this.http.delete<any>(this.panierList + '/' + id, httpOptions);
  }

    deletePanierByUser(id: number): Observable<any> {
    return this.http.delete<any>(this.panierList + '/deleteByUser/' + id, httpOptions);
  }
}
