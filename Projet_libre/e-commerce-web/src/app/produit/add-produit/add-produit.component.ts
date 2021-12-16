import { Component, OnInit } from '@angular/core';
import { Produit } from 'src/app/model/produit-model';
import { User } from 'src/app/model/user-model';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { ProduitService } from 'src/app/services/produit.service';
import { Router } from '@angular/router';
import { SaveTokenService } from 'src/app/auth/save-token.service';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-add-produit',
  templateUrl: './add-produit.component.html',
  styleUrls: ['./add-produit.component.css']
})
export class AddProduitComponent implements OnInit {
  editForm: FormGroup;
  user: User;
  produit: Produit;
  public button: string;
  public userId: string;

  constructor(private formBuilder: FormBuilder, private router: Router, private token: SaveTokenService, private userService: UserService, private produitService: ProduitService) { }

  ngOnInit() {

    const produitId = window.localStorage.getItem('editProduit');

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
      reference: ['', Validators.required],
      prix: ['', Validators.required],
      nom: ['', Validators.required],
      description: ['', Validators.required],
      stock: ['', Validators.required],
      url: ['', Validators.required],
      user: [this.user, Validators.required],
    });

    if (produitId) {
      this.button = 'edit';
      this.produitService.getProduitById(+produitId).subscribe (
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
      this.produitService.createProduit(this.editForm.value).subscribe(
        data => {
          this.produit = data;
          this.router.navigateByUrl('produit');
        }
      );
    } else if (this.button === 'edit') {
      this.produitService.updateProduit(this.editForm.value).subscribe(
        data => {
          this.produit = data;
          window.localStorage.removeItem('editProduit');
          this.router.navigateByUrl('produit');
        }
      );
    }
  }

  goBack() {
    this.router.navigate(["/produit"])
  }
}
