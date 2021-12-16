import { Component, NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, Routes } from '@angular/router';

import { RegisterComponent } from './register/register.component';
import { LoginComponent } from './login/login.component';
import { HomeComponent } from './home/home.component';
import { UserComponent } from './user/user.component';
import { EditUserComponent } from './user/edit-user/edit-user.component';
import { AddressComponent } from './address/address.component';
import { AddAddressComponent } from './address/add-address/add-address.component';
import { EditAddressComponent } from './address/edit-address/edit-address.component';
import { ProduitComponent } from './produit/produit.component';
import { AddProduitComponent } from './produit/add-produit/add-produit.component';
import { EditProduitComponent} from './produit/edit-produit/edit-produit.component';
import { CommentaireComponent } from './commentaire/commentaire.component';
import { AddCommentaireComponent } from './commentaire/add-commentaire/add-commentaire.component';
import { PanierComponent } from './panier/panier.component';
import { AddPanierComponent } from './panier/add-panier/add-panier.component';
import { FacturationComponent } from './facturation/facturation.component';
import { AddFacturationComponent } from './facturation/add-facturation/add-facturation.component';
import { ProfilComponent } from './profil/profil.component';
import { EditCommentaireComponent } from './commentaire/edit-commentaire/edit-commentaire.component';
import { EditPanierComponent } from './panier/edit-panier/edit-panier.component';
import { EditFacturationComponent } from './facturation/edit-facturation/edit-facturation.component';

const routes: Routes = [
    {
        path: 'home',
        component: HomeComponent
    },
    {
        path: 'user',
        component: UserComponent
    },
    {
        path: 'editUser',
        component: EditUserComponent
    },
    {
        path: 'auth/login',
        component: LoginComponent
    },
    {
        path: 'signup',
        component: RegisterComponent
    },
    {
        path: 'address',
        component: AddressComponent
    },
    {
        path: 'addAddress',
        component: AddAddressComponent
    },
    {
        path: 'editAddress/:id',
        component: EditAddressComponent
    },
    {
        path: 'produit',
        component: ProduitComponent
    },
    {
        path: 'addProduit',
        component: AddProduitComponent
    },
    {
        path: 'editProduit/:id',
        component: EditProduitComponent
    },
    {
        path: 'commentaire',
        component: CommentaireComponent
    },
    {
        path: 'addCommentaire',
        component: AddCommentaireComponent
    },
    {
        path: 'editCommentaire/:id',
        component: EditCommentaireComponent
    },
    {
        path: 'panier',
        component: PanierComponent
    },
    {
        path: 'addPanier',
        component : AddPanierComponent
    },
    {
        path: 'editPanier/:id',
        component: EditPanierComponent
    },
    {
        path: 'facturation',
        component: FacturationComponent
    },
    {
        path: 'addFacturation',
        component: AddFacturationComponent
    },
    {
        path: 'editFacturation/:id',
        component: EditFacturationComponent
    },
    {
        path: 'profil',
        component: ProfilComponent
    },
    {
        path: '',
        redirectTo: 'home',
        pathMatch: 'full'
    }
];

@NgModule({
  declarations: [],
  imports: [
    CommonModule,
    RouterModule.forRoot(routes)
  ],
  exports: [RouterModule]
})
export class AppRoutingModule { }
