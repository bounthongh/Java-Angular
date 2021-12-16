import { Component, OnInit } from '@angular/core';
import { SaveTokenService } from './auth/save-token.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  private roles: string;
  public authority: string;
  public username: string;

  constructor(private saveToken: SaveTokenService, private router: Router) { }

  ngOnInit() {
    if (this.saveToken.getToken()) {
      this.username = this.saveToken.getUsername();
      this.roles = this.saveToken.getAuthorities();
      if (this.roles === '"ROLE_ADMIN"') {
        this.authority = 'admin';
        return true;
      }
      this.authority = 'user';
      return true;
    } else {
      this.router.navigateByUrl('signup');
    }
  }

  logout() {
    this.saveToken.signOut();
    window.location.reload();
  }

}
