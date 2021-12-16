import { Component, OnInit } from '@angular/core';
import { AuthService } from '../auth/auth.service';
import { SignUpInfo } from '../auth/signupInfo';
import { SaveTokenService } from '../auth/save-token.service';
import { LoginInfo } from '../auth/loginInfo';
import { Router } from '@angular/router';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {
  form: any = {};
  signupInfo: SignUpInfo;
  loginInfo: LoginInfo;
  isSignedUp = false;
  isSignUpFailed = false;
  errorMessage = '';


  constructor(private authService: AuthService, private saveToken: SaveTokenService, private router: Router) { }

  ngOnInit() {
  }

   onSubmit() {
    console.log(this.form);

    this.signupInfo = new SignUpInfo(
      this.form.username,
      this.form.password,
      this.form.Role);

    this.authService.signUp(this.signupInfo).subscribe(
       data => {
        console.log('data: ' + data);
        this.isSignedUp = true;
        this.isSignUpFailed = false;
        this.router.navigateByUrl('/auth/login');
      },
      error => {
        console.log(error);
        this.errorMessage = error.message;
        this.isSignUpFailed = true;
      }
    );
  }
  reloadPage() {
    window.location.reload();
  }
}
