import { Component, OnInit } from '@angular/core';
import { Produit } from '../model/produit-model';
import { ProduitService } from '../services/produit.service';
import { SaveTokenService } from '../auth/save-token.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-produit',
  templateUrl: './produit.component.html',
  styleUrls: ['./produit.component.css']
})
export class ProduitComponent implements OnInit {
  public roles: string;
  public authority: string;
  term: any;
  public userId: number;
  produits: Produit[];

  constructor(private produitService: ProduitService, private token: SaveTokenService, private router: Router) { }

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

    this.produitService.getProduitBoard()
      .subscribe( data => {
        this.produits = data;
      });
  }

  createProduit(): void {
    this.router.navigateByUrl('addProduit');
  }

  editProduit(produit: Produit): void {
    window.localStorage.removeItem('editProduit');
    window.localStorage.setItem('editProduit', produit.id.toString());
    this.router.navigateByUrl('addProduit');
  }

  deleteProduit(produit: Produit): void {
    this.produitService.deleteProduit(produit.id)
      .subscribe(data => {
        console.log(data);
        this.produitService.getProduitBoard()
      .subscribe( data => {
        this.produits = data;
      });
      });

    this.reloadPage();
  }

  reloadPage() {
    window.location.reload();
  }

}
