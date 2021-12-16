import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { User } from '../model/user-model';

const httpOptions = {
   headers: new HttpHeaders({ 'Content-Type': 'application/json', 'Access-Control-Allow-Origin': '*', 'Access-Control-Allow-Methods': '*'})
};

@Injectable({
  providedIn: 'root'
})

export class UserService {

  private userList = 'http://localhost:8090/user';

  constructor(private http: HttpClient) { }

  getUserBoard(): Observable<User[]> {
    return this.http.get<User[]>(this.userList, httpOptions);
  }

  getUserById(id: number): Observable<User> {
    return this.http.get<User>(this.userList + '/' + id, httpOptions);
  }

  updateUser(user: User): Observable<User> {
    return this.http.put<User>(this.userList + '/' + user.id, user, httpOptions);
  }

  deleteUser(id: number): Observable<any> {
    return this.http.delete<any>(this.userList + '/' + id, httpOptions);
  }
}
