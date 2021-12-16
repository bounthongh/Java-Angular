import { Component, OnInit } from '@angular/core';
import { Commentaire } from '../model/commentaire-model';
import { CommentaireService } from '../services/commentaire.service';
import { Router } from '@angular/router';
import { SaveTokenService } from '../auth/save-token.service';

@Component({
  selector: 'app-commentaire',
  templateUrl: './commentaire.component.html',
  styleUrls: ['./commentaire.component.css']
})
export class CommentaireComponent implements OnInit {
  public roles: string;
  public authority: string;
  term: any;
  public userId: number;
  commentaires: Commentaire[];

  constructor(private commentaireService: CommentaireService, private token: SaveTokenService, private router: Router) { }

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

    this.commentaireService.getCommentaireBoard()
      .subscribe(
        data => {
          this.commentaires = data;
        }
      );
  }

  createCommentaire(): void {
    this.router.navigateByUrl('addCommentaire');
  }

  editCommentaire(commentaire: Commentaire): void {
    window.localStorage.removeItem('editCommentaire');
    window.localStorage.setItem('editCommentaire', commentaire.id.toString());
    this.router.navigateByUrl('addCommentaire');
  }

  deleteCommentaire(commentaire: Commentaire): void {
    this.commentaireService.deleteCommentaire(commentaire.id)
      .subscribe(
        data => {
          console.log(data);
          this.commentaireService.getCommentaireBoard()
      .subscribe(
        // tslint:disable-next-line: no-shadowed-variable
        data => {
          this.commentaires = data;
        });
        });

    this.reloadPage();
  }

  reloadPage() {
    window.location.reload();
  }

}
