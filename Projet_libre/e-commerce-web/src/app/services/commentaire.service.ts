import { Injectable } from '@angular/core';
import { HttpHeaders, HttpClient } from '@angular/common/http';
import { Commentaire } from '../model/commentaire-model';
import { Observable } from 'rxjs';

const httpOptions = {
   headers: new HttpHeaders({ 'Content-Type': 'application/json', 'Access-Control-Allow-Origin': '*', 'Access-Control-Allow-Methods': '*'})
};

@Injectable({
  providedIn: 'root'
})
export class CommentaireService {

  private commentaireList = 'http://localhost:8090/commentaire';

  constructor(private http: HttpClient) { }

  getCommentaireBoard(): Observable<Commentaire[]> {
    return this.http.get<Commentaire[]>(this.commentaireList, httpOptions);
  }

  getCommentaireById(id: number): Observable<Commentaire> {
    return this.http.get<Commentaire>(this.commentaireList + '/' + id, httpOptions);
  }

  createCommentaire(commentaire: Commentaire): Observable<Commentaire> {
    return this.http.post<Commentaire>(this.commentaireList, commentaire, httpOptions);
  }

  updateCommentaire(commentaire: Commentaire): Observable<Commentaire> {
    return this.http.put<Commentaire>(this.commentaireList + '/' + commentaire.id, commentaire, httpOptions);
  }

  deleteCommentaire(id: number): Observable<any> {
    return this.http.delete<any>(this.commentaireList + '/' + id, httpOptions);
  }

  deleteCommentaireByUser(id: number): Observable<any> {
    return this.http.delete<any>(this.commentaireList + '/deleteByUser/' + id, httpOptions);
  }
}
