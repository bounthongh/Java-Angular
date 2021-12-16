import { Component, OnInit } from '@angular/core';
import { Address } from 'src/app/model/address-model';
import { User } from 'src/app/model/user-model';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { AddressService } from 'src/app/services/address.service';
import { Router } from '@angular/router';
import { SaveTokenService } from 'src/app/auth/save-token.service';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-add-address',
  templateUrl: './add-address.component.html',
  styleUrls: ['./add-address.component.css']
})
export class AddAddressComponent implements OnInit {
  editForm: FormGroup;
  user: User;
  address: Address;
  public button: string;
  public userId: string;

  constructor(private formBuilder: FormBuilder, private router: Router, private token: SaveTokenService, private userService: UserService, private addressService: AddressService) { }

  ngOnInit() {

    const addressId = window.localStorage.getItem('editAddress');

    if (!this.token.getToken) {
      this.router.navigateByUrl('home');
      return;
    }

    this.userId = this.token.getId();

    this.userService.getUserById(+this.userId).subscribe (
      data => {
        this.user = data;
      });

    this.editForm = this.formBuilder.group({
      id: [''],
      road: ['', Validators.required],
      postalCode: ['', Validators.required],
      city: ['', Validators.required],
      country: ['', Validators.required],
      user: [this.user, Validators.required],
    });

    if (addressId) {
      this.button = 'edit';
      this.addressService.getAddressById(+addressId).subscribe (
        data => {
          this.editForm.setValue(data);
        }
      );
    } else {
      this.button = 'add';
    }
  }

  onSubmit() {
    if (this.button === 'add') {
      this.addressService.createAddress(this.editForm.value).subscribe(
        data => {
          this.address = data;
          this.router.navigateByUrl('address');
        }
      );
    } else if (this.button === 'edit') {
      this.addressService.updateAddress(this.editForm.value).subscribe(
        data => {
          this.address = data;
          window.localStorage.removeItem('editAddress');
          this.router.navigateByUrl('address');
        }
      );
    }
  }

  goBack() {
    this.router.navigate(["/address"])
  }
}
