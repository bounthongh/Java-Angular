import { Component, OnInit } from '@angular/core';

import { UserService } from '../services/user.service';
import { User } from '../model/user-model';
import { Router } from '@angular/router';
import { SaveTokenService } from '../auth/save-token.service';
import { AddressService } from '../services/address.service';
import { CommentaireService } from '../services/commentaire.service';
import { PanierService } from '../services/panier.service';
import { FacturationService } from '../services/facturation.service';
import { ProduitService } from '../services/produit.service';

@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.css']
})
export class UserComponent implements OnInit {
  public roles: string;
  public authority: string;
  users: User[];
  term: any;
  userId: number;

  // tslint:disable-next-line: max-line-length
  constructor(private userService: UserService, private token: SaveTokenService, private router: Router, private addressService: AddressService,
              // tslint:disable-next-line: max-line-length
              private commentaireService: CommentaireService, private facturationService: FacturationService, private panierService: PanierService, private produitService: ProduitService) { }

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

     this.userService.getUserBoard()
      .subscribe( data => {
        this.users = data;
      });
  }

  deleteUser(user: User): void {

    this.facturationService.deleteFacturationByUser(user.id)
      .subscribe( data => {
        console.log(data);
    });

    this.panierService.deletePanierByUser(user.id)
      .subscribe( data => {
        console.log(data);
    });

    this.commentaireService.deleteCommentaireByUser(user.id)
      .subscribe( data => {
        console.log(data);
    });

    this.produitService.deleteProduitByUser(user.id)
      .subscribe( data => {
        console.log(data);
    });

    this.addressService.deleteAddressByUser(user.id)
      .subscribe( data => {
         console.log(data);
    });

    this.userService.deleteUser(user.id)
      .subscribe( data => {
        console.log(data);
        // tslint:disable-next-line: no-shadowed-variable
        this.userService.getUserBoard().subscribe( data => {
        this.users = data;
       });
      });

    this.reloadPage();
  }

  editUser(user: User): void {
    window.localStorage.removeItem('editUserId');
    window.localStorage.setItem('editUserId', user.id.toString());
    this.router.navigateByUrl('editUser');
  }

  reloadPage() {
    window.location.reload();
  }

}
