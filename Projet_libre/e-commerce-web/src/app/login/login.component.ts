import { Component, OnInit } from '@angular/core';
import { AuthService } from '../auth/auth.service';
import { SaveTokenService } from '../auth/save-token.service';
import { LoginInfo } from '../auth/loginInfo';
import { Router } from '@angular/router';


@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  form: any = {};
  isLoggedIn = false;
  isLoginFailed = false;
  errorMessage = '';
  roles = '';
  token = '';
  user = '';

  private loginInfo: LoginInfo;

  constructor(private authService: AuthService, private saveToken: SaveTokenService, private router: Router) { }

  ngOnInit() {
    if (this.saveToken.getToken()) {
      this.isLoggedIn = true;
      console.log(this.saveToken);
      this.roles = this.saveToken.getAuthorities();
      this.token = this.saveToken.getToken();
      this.user = this.saveToken.getUsername();
      this.router.navigateByUrl('home');
    }
  }

  onSubmit() {
    console.log(this.form);

    this.loginInfo = new LoginInfo(
      this.form.username,
      this.form.password);

    this.authService.attemptAuth(this.loginInfo).subscribe(
   data => {
        this.saveToken.saveToken(data.token);
        console.log('token:');
        console.log(data.token);
        this.saveToken.saveUsername(this.form.username);
        console.log(this.roles);
        console.log(this.saveToken);
        this.roles = this.saveToken.getAuthorities();
        this.isLoginFailed = false;
        this.isLoggedIn = true;
        this.reloadPage();
      },
      error => {
        console.log(error);
        console.log(error.error.message);
        this.errorMessage = error.error.message;
        this.isLoginFailed = true;
      }

    );
  }

  reloadPage() {
    window.location.reload();
  }
}
