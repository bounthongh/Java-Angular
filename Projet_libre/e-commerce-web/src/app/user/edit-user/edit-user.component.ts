import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { SaveTokenService } from 'src/app/auth/save-token.service';
import { UserService } from 'src/app/services/user.service';
import { User } from 'src/app/model/user-model';

@Component({
  selector: 'app-edit-user',
  templateUrl: './edit-user.component.html',
  styleUrls: ['./edit-user.component.css']
})
export class EditUserComponent implements OnInit {

  editForm: FormGroup;
  user: User;

  constructor(private formBuilder: FormBuilder, private router: Router, private userService: UserService, private token: SaveTokenService) { }

  ngOnInit() {
    const userId = window.localStorage.getItem('editUserId');

    if (!this.token.getToken) {
      this.router.navigateByUrl('home');
      return;
    }

    if (!userId) {
      this.router.navigateByUrl('user');
      return;
    }

    this.editForm = this.formBuilder.group({
      id: [''],
      username: ['', Validators.required],
      Role: ['', Validators.required],
    });

    this.userService.getUserById(+userId).subscribe (
      data => {
        this.editForm.setValue(data);
      });
  }

  onSubmit() {
    this.userService.updateUser(this.editForm.value).subscribe(
      data => {
        this.user = data;
        window.localStorage.removeItem('editUserId');
        this.router.navigateByUrl('user');
      });
  }
}
