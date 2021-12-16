import { Injectable } from '@angular/core';
import { HttpHeaders, HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Produit } from '../model/produit-model';

const httpOptions = {
   headers: new HttpHeaders({ 'Content-Type': 'application/json', 'Access-Control-Allow-Origin': '*', 'Access-Control-Allow-Methods': '*'})
};

@Injectable({
  providedIn: 'root'
})

export class ProduitService {

  private produitList = 'http://localhost:8090/produit';

  constructor(private http: HttpClient) { }

  getProduitBoard(): Observable<Produit[]> {
    return this.http.get<Produit[]>(this.produitList, httpOptions);
  }

  getProduitById(id: number): Observable<Produit> {
    return this.http.get<Produit>(this.produitList + '/' + id, httpOptions);
  }

  createProduit(produit: Produit): Observable<Produit> {
    return this.http.post<Produit>(this.produitList, produit, httpOptions);
  }

  updateProduit(produit: Produit): Observable<Produit> {
    return this.http.put<Produit>(this.produitList + '/' + produit.id, produit, httpOptions);
  }

  deleteProduit(id: number): Observable<any> {
    return this.http.delete<any>(this.produitList + '/' + id, httpOptions);
  }

  deleteProduitByUser(id: number): Observable<any> {
    return this.http.delete<any>(this.produitList + '/deleteByUser/' + id, httpOptions);
  }
}
