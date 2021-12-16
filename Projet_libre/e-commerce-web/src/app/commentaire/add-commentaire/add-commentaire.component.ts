import { Component, OnInit } from '@angular/core';
import { Commentaire } from 'src/app/model/commentaire-model';
import { Produit } from 'src/app/model/produit-model';
import { User } from 'src/app/model/user-model';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { CommentaireService } from 'src/app/services/commentaire.service';
import { ProduitService } from 'src/app/services/produit.service';
import { Router } from '@angular/router';
import { SaveTokenService } from 'src/app/auth/save-token.service';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-add-commentaire',
  templateUrl: './add-commentaire.component.html',
  styleUrls: ['./add-commentaire.component.css']
})
export class AddCommentaireComponent implements OnInit {
  editForm: FormGroup;
  commentaire : Commentaire;
  user: User;
  produit: Produit;
  public button: string;
  public userId: string;
  public produitId : string;

  constructor(private formBuilder: FormBuilder, private router: Router, private token: SaveTokenService, private commentaireService : CommentaireService ,private userService: UserService, private produitService: ProduitService) { }

  ngOnInit() {

    const commentaireId = window.localStorage.getItem('editCommentaire');

    if (!this.token.getToken) {
      this.router.navigateByUrl('home');
      return;
    }

    this.userId = this.token.getId();

    this.userService.getUserById(+this.userId).subscribe (
      data => {
        this.user = data;
    });

    this.produitService.getProduitById(+this.produitId).subscribe (
      data => {
        this.produit = data;
    });

    this.editForm = this.formBuilder.group({
      id: [''],
      commentaire: ['', Validators.required],
      note: ['', Validators.required],
      user: [this.user, Validators.required],
      produit: [this.produit, Validators.required],
    });

    if (commentaireId) {
      this.button = 'edit';
      this.commentaireService.getCommentaireById(+commentaireId).subscribe (
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
      this.commentaireService.createCommentaire(this.editForm.value).subscribe(
        data => {
          this.commentaire = data;
          this.router.navigateByUrl('commentaire');
        }
      );
    } else if (this.button === 'edit') {
      this.commentaireService.updateCommentaire(this.editForm.value).subscribe(
        data => {
          this.commentaire = data;
          window.localStorage.removeItem('editCommentaire');
          this.router.navigateByUrl('commentaire');
        }
      );
    }
  }

  goBack() {
    this.router.navigate(["/commentaire"])
  }
}
