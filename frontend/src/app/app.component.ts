import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { TokenStorageService } from './token-storage.service';


@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.sass']
})
export class AppComponent implements OnInit {

  title = 'frontend';

  isLoggedIn = false;
  isLoggedOut = true;

  constructor(private tokenStorage: TokenStorageService, private router: Router) {}

  ngOnInit(): void {
    this.isLoggedIn = !!this.tokenStorage.getToken();
  }

  onLogin(event: boolean): void {
    this.isLoggedIn = event;
    this.router.navigate(['/home']);
  }

  onLogout(event: boolean): void {
    this.isLoggedIn = false;
    this.isLoggedOut = event;
  }

}
