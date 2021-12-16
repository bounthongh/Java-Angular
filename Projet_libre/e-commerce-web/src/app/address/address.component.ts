import { Component, OnInit } from '@angular/core';
import { AddressService } from '../services/address.service';
import { Address } from '../model/address-model';
import { SaveTokenService } from '../auth/save-token.service';
import { Router } from '@angular/router';


@Component({
  selector: 'app-address',
  templateUrl: './address.component.html',
  styleUrls: ['./address.component.css']
})
export class AddressComponent implements OnInit {
  public roles: string;
  public authority: string;
  term: any;
  public userId: number;
  address: Address[];

  constructor(private addressService: AddressService, private token: SaveTokenService, private router: Router) { }

  ngOnInit() {
     if (!this.token.getToken) {
      this.router.navigateByUrl('home');
      return;
    }

     this.roles = this.token.getAuthorities();
     this.userId = +this.token.getId();

     if (this.roles === '"ROLE_ADMIN"') {
        this.authority = 'admin';
      } else {
          this.authority = 'user';
      }

     this.addressService.getAddressBoard()
        .subscribe( data => {
          this.address = data;
        });
  }

  createAddress(): void {
    this.router.navigateByUrl('addAddress');
  }

  editAdresse(address: Address): void {
    window.localStorage.removeItem('editAddress');
    window.localStorage.setItem('editAddress', address.id.toString());
    this.router.navigateByUrl('addAddress');
  }

  deleteAdresse(address: Address): void {
    this.addressService.deleteAddress(address.id)
      .subscribe( data => {
        console.log(data);
        this.addressService.getAddressBoard()
        // tslint:disable-next-line: no-shadowed-variable
        .subscribe( data => {
          this.address = data;
        });
         });

    this.reloadPage();
  }

  reloadPage() {
    window.location.reload();
  }
}
