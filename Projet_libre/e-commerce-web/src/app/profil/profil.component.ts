import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { User } from '../model/user-model';
import { Router } from '@angular/router';
import { SaveTokenService } from '../auth/save-token.service';
import { UserService } from '../services/user.service';
import { AddressService } from '../services/address.service';
import { CommentaireService } from '../services/commentaire.service';
import { FacturationService } from '../services/facturation.service';
import { PanierService } from '../services/panier.service';
import { ProduitService } from '../services/produit.service';

@Component({
  selector: 'app-profil',
  templateUrl: './profil.component.html',
  styleUrls: ['./profil.component.css']
})
export class ProfilComponent implements OnInit {

  editForm: FormGroup;
  user: User;
  userId: string;

  // tslint:disable-next-line: max-line-length
  constructor(private formBuilder: FormBuilder, private router: Router, private token: SaveTokenService, private userService: UserService,  private addressService: AddressService,
              // tslint:disable-next-line: max-line-length
              private commentaireService: CommentaireService, private facturationService: FacturationService, private panierService: PanierService, private produitService: ProduitService) { }

  ngOnInit() {
    if (!this.token.getToken) {
      this.router.navigateByUrl('home');
      return;
    }

    this.userId = this.token.getId();

    if (!this.userId) {
      this.router.navigateByUrl('user');
      return;
    }

    this.editForm = this.formBuilder.group({
      id: [''],
      Role: ['', Validators.required],
    });

    this.userService.getUserById(+this.userId).subscribe (
      data => {
        this.editForm.setValue(data);
    });
  }

  onSubmit() {
    this.userService.updateUser(this.editForm.value).subscribe(
      data => {
        this.user = data;
        this.token.saveAuthorities(this.user.Role);
        this.reloadPage();
      });
  }

  onSubmitDelete() {

    this.facturationService.deleteFacturationByUser(+this.userId)
      .subscribe( data => {
        console.log(data);
    });

    this.panierService.deletePanierByUser(+this.userId)
      .subscribe( data => {
        console.log(data);
    });

    this.commentaireService.deleteCommentaireByUser(+this.userId)
      .subscribe( data => {
        console.log(data);
    });

    this.produitService.deleteProduitByUser(+this.userId)
      .subscribe( data => {
        console.log(data);
    });

    this.addressService.deleteAddressByUser(+this.userId)
      .subscribe( data => {
         console.log(data);
    });

    this.userService.deleteUser(+this.userId)
      .subscribe( data => {
        console.log(data);
        this.logout();
    });
  }

  reloadPage() {
    window.location.reload();
  }

  logout() {
    this.token.signOut();
    window.location.reload();
  }

}
