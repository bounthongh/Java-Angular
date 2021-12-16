import { Component, OnInit } from '@angular/core';
import { Panier } from 'src/app/model/panier-model';
import { User } from 'src/app/model/user-model';
import { Produit } from 'src/app/model/produit-model';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { PanierService } from 'src/app/services/panier.service';
import { ProduitService } from 'src/app/services/produit.service';
import { Router } from '@angular/router';
import { SaveTokenService } from 'src/app/auth/save-token.service';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-add-panier',
  templateUrl: './add-panier.component.html',
  styleUrls: ['./add-panier.component.css']
})
export class AddPanierComponent implements OnInit {
  editForm: FormGroup;
  panier: Panier;
  user: User;
  produit: Produit;
  public button: string;
  public userId: string;

  constructor(private formBuilder: FormBuilder, private router: Router, private token: SaveTokenService, private panierService: PanierService, private userService: UserService, private produitService: ProduitService) { }

  ngOnInit() {

    const panierId = window.localStorage.getItem('editPanier');

    if (!this.token.getToken) {
      this.router.navigateByUrl('home');
      return;
    }

    this.userId = this.token.getId();

    this.userService.getUserById(+this.userId).subscribe (
      data => {
        this.user = data;
      });

    this.produitService.getProduitById(+this.userId).subscribe (
      data => {
        this.produit = data;
      });

    this.editForm = this.formBuilder.group({
      id: [''],
      quantite: ['', Validators.required],
      user: [this.user, Validators.required],
      produit: [this.produit, Validators.required],
    });

    if (panierId) {
      this.button = 'edit';
      this.panierService.getPanierById(+panierId).subscribe (
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
      this.panierService.createPanier(this.editForm.value).subscribe(
        data => {
          this.panier = data;
          this.router.navigateByUrl('panier');
        }
      );
    } else if (this.button === 'edit') {
      this.panierService.updatePanier(this.editForm.value).subscribe(
        data => {
          this.panier = data;
          window.localStorage.removeItem('editPanier');
          this.router.navigateByUrl('panier');
        }
      );
    }
  }
  goBack() {
    this.router.navigate(["/panier"])
  }
}
