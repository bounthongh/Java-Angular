import { Component, OnInit } from '@angular/core';

import { PanierService } from '../services/panier.service';
import { Panier } from '../model/panier-model';
import { Router } from '@angular/router';
import { SaveTokenService } from '../auth/save-token.service';

@Component({
  selector: 'app-panier',
  templateUrl: './panier.component.html',
  styleUrls: ['./panier.component.css']
})
export class PanierComponent implements OnInit {
  public roles: string;
  public authority: string;
  term: any;
  public userId: number;
  paniers: Panier[];

  constructor(private panierService: PanierService, private token: SaveTokenService, private router: Router) { }

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

    this.panierService.getPanierBoard()
      .subscribe( data => {
        this.paniers = data;
      });
  }

  createPanier(): void {
    this.router.navigateByUrl('addPanier');
  }

  editPanier(panier: Panier): void {
    window.localStorage.removeItem('editPanier');
    window.localStorage.setItem('editPanier', panier.id.toString());
    this.router.navigateByUrl('addPanier');
  }

  deletePanier(panier: Panier): void {
    this.panierService.deletePanier(panier.id)
      .subscribe(data => {
        console.log(data);
        this.panierService.getPanierBoard()
      .subscribe( data => {
        this.paniers = data;
      });
      });

    this.reloadPage();
  }

  reloadPage() {
    window.location.reload();
  }

}
