import { Component, OnInit } from '@angular/core';
import { Facturation } from '../model/facturation-model';
import { Router } from '@angular/router';
import { SaveTokenService } from '../auth/save-token.service';
import { FacturationService } from '../services/facturation.service';

@Component({
  selector: 'app-facturation',
  templateUrl: './facturation.component.html',
  styleUrls: ['./facturation.component.css']
})
export class FacturationComponent implements OnInit {

  facturations: Facturation[];
  term: any;
  public roles: string;
  public authority: string;
  public userId: number;

  constructor(private facturationService: FacturationService, private token: SaveTokenService, private router: Router) { }

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

    this.facturationService.getFacturationBoard()
      .subscribe(
        data => {
          this.facturations = data;
        }
      );
  }

  createFacturation(): void {
    this.router.navigateByUrl('addFacturation');
  }

  editFacturation(facturation: Facturation): void {
    window.localStorage.removeItem('editFacturation');
    window.localStorage.setItem('editFacturation', facturation.id.toString());
    this.router.navigateByUrl('addFacturation');
  }

  updateFacturation(): void {
    this.router.navigateByUrl('updateFacturation');
  }

  deleteFacturation(facturation: Facturation): void {
    this.facturationService.deleteFacturation(facturation.id)
      .subscribe( data => {
        console.log(data);
        this.facturationService.getFacturationBoard()
        .subscribe( data => {
          this.facturations = data;
        });
         });

    this.reloadPage();
  }


  reloadPage() {
    window.location.reload();
  }

}
