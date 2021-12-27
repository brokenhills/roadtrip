import { Component } from '@angular/core';
import { TokenStorageService } from './token-storage.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.sass']
})
export class AppComponent {
  title = 'frontend';

  private role: string = '';
  isLoggedIn = false;
  showUserBoard = false;
  showAdminBoard = false;
  username?: string;

  constructor(private tokenStorageService: TokenStorageService) {}

  ngOnInit(): void {
    this.isLoggedIn = !!this.tokenStorageService.getToken();

    if (this.isLoggedIn) {
      const user = this.tokenStorageService.getUser();
      this.role = user.role;
      this.showAdminBoard = this.role === 'ROLE_USER';
      this.showAdminBoard = this.role === 'ROLE_ADMIN';
      this.username = user.username;
    }
  }

  logout(): void {
    this.tokenStorageService.logout();
    window.location.reload();
  }

}
