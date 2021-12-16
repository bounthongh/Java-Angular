import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { HttpClientModule } from '@angular/common/http';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { AppComponent } from './app.component';
import { LoginComponent } from './login/login.component';
import { RegisterComponent } from './register/register.component';
import { HomeComponent } from './home/home.component';
import { UserComponent } from './user/user.component';

import { httpInterceptorProviders } from './auth/interceptor';
import { AppRoutingModule } from './app-routing.module';
import { EditUserComponent } from './user/edit-user/edit-user.component';
import { Ng2SearchPipeModule } from 'ng2-search-filter';
import { AddressComponent } from './address/address.component';
import { AddAddressComponent } from './address/add-address/add-address.component';
import { ProduitComponent } from './produit/produit.component';
import { AddProduitComponent } from './produit/add-produit/add-produit.component';
import { CommentaireComponent } from './commentaire/commentaire.component';
import { AddCommentaireComponent } from './commentaire/add-commentaire/add-commentaire.component';
import { PanierComponent } from './panier/panier.component';
import { AddPanierComponent } from './panier/add-panier/add-panier.component';
import { FacturationComponent } from './facturation/facturation.component';
import { AddFacturationComponent } from './facturation/add-facturation/add-facturation.component';
import { ProfilComponent } from './profil/profil.component';
import { EditProduitComponent } from './produit/edit-produit/edit-produit.component';
import { EditAddressComponent } from './address/edit-address/edit-address.component';
import { EditCommentaireComponent } from './commentaire/edit-commentaire/edit-commentaire.component';
import { EditFacturationComponent } from './facturation/edit-facturation/edit-facturation.component';
import { EditPanierComponent } from './panier/edit-panier/edit-panier.component';


@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    RegisterComponent,
    HomeComponent,
    UserComponent,
    EditUserComponent,
    AddressComponent,
    AddAddressComponent,
    ProduitComponent,
    AddProduitComponent,
    CommentaireComponent,
    AddCommentaireComponent,
    PanierComponent,
    FacturationComponent,
    ProfilComponent,
    AddFacturationComponent,
    AddPanierComponent,
    EditProduitComponent,
    EditAddressComponent,
    EditCommentaireComponent,
    EditFacturationComponent,
    EditPanierComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    Ng2SearchPipeModule,
    ReactiveFormsModule
  ],
  providers: [httpInterceptorProviders],
  bootstrap: [AppComponent]
})
export class AppModule { }
