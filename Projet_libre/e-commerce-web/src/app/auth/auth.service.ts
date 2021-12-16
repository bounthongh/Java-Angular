import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JwtResponse } from './jwtResponse';
import { LoginInfo } from './loginInfo';
import { SignUpInfo } from './signupInfo';
import { MeResponse } from './me-reponse';

const httpOptions = {
   headers: new HttpHeaders({ 'Content-Type': 'application/json', 'Access-Control-Allow-Origin': '*', 'Access-Control-Allow-Methods': '*'})
};

@Injectable({
  providedIn: 'root'
})

export class AuthService {

  private loginUrl = 'http://localhost:8090/authenticate';
  private signupUrl = 'http://localhost:8090/register';
  private connectUrl = 'http://localhost:8090/me';

  constructor(private http: HttpClient) {
  }

  attemptAuth(credentials: LoginInfo): Observable<JwtResponse> {
    return this.http.post<JwtResponse>(this.loginUrl, credentials, httpOptions);
  }

  signUp(info: SignUpInfo): Observable<string> {
    return this.http.post<string>(this.signupUrl, info, httpOptions);
  }
  connectAuth(): Observable<MeResponse> {
    return this.http.get<MeResponse>(this.connectUrl, httpOptions);
  }

}
