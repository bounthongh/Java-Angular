import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Facturation } from 'src/app/model/facturation-model';
import { Address } from 'src/app/model/address-model';
import { Router } from '@angular/router';
import { FacturationService } from 'src/app/services/facturation.service';
import { PanierService } from 'src/app/services/panier.service';
import { AddressService } from 'src/app/services/address.service';
import { Panier } from 'src/app/model/panier-model';
import { SaveTokenService } from 'src/app/auth/save-token.service';

@Component({
  selector: 'app-add-facturation',
  templateUrl: './add-facturation.component.html',
  styleUrls: ['./add-facturation.component.css']
})
export class AddFacturationComponent implements OnInit {
  
  editForm: FormGroup;
  facturation: Facturation;
  address: Address;
  panier: Panier;
  public button: string;
  public userId: string;
  public addressId: string;
  public panierId : string;

  constructor(private formBuilder: FormBuilder, private router: Router, private token: SaveTokenService, private facturationService: FacturationService, private panierService: PanierService, private addressService: AddressService ) { }

  ngOnInit() {
    const facturationId = window.localStorage.getItem('editFacturation');
    if (!this.token.getToken) {
      this.router.navigateByUrl('home');
      return;
    }
    this.userId = this.token.getId();

    this.addressService.getAddressById(+this.addressId).subscribe (
    data => {
      this.address = data;
    });

    this.panierService.getPanierById(+this.panierId).subscribe (
      data => {
        this.panier = data;
    });

    this.editForm = this.formBuilder.group({
    id: [''],
    totalQt: ['', Validators.required],
    totalPrix: ['', Validators.required],
    payement: ['', Validators.required],
    livraison: ['', Validators.required],
    panier: [this.panier, Validators.required],
    address: [this.address, Validators.required],
    });

    if (facturationId) {
      this.button = 'edit';
      this.facturationService.getFacturationById(+facturationId).subscribe (
      data => {
        this.editForm.setValue(data);
      });
    } else {
    this.button = 'add';
    }

  }

  onSubmit() {

  if (this.button === 'add') {
   this.facturationService.createFacturation(this.editForm.value).subscribe(
   data => {
    this.facturation = data;
    this.router.navigateByUrl('facturation');
  });
  } else if (this.button === 'edit') {
      this.facturationService.updateFacturation(this.editForm.value).subscribe(
      data => {
        this.facturation = data;
        window.localStorage.removeItem('editFacturation');
        this.router.navigateByUrl('facturation');
      });
   }
  }

  goBack() {
    this.router.navigate(["/facturation"])
  }
}
