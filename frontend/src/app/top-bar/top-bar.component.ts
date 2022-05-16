import { Component, EventEmitter, Input, Output } from '@angular/core';
import { TokenStorageService } from '../token-storage.service';

@Component({
  selector: 'app-top-bar',
  templateUrl: './top-bar.component.html',
  styleUrls: ['./top-bar.component.sass']
})
export class TopBarComponent {
  
  @Input()
  isLoggedIn = false;
  @Output()
  logoutEvent: EventEmitter<boolean> = new EventEmitter();

  username?: string;

  menuItems: Array<any> = [
    { title: 'Home', route: '/home', onClick: () =>  '' },
    { title: 'Workflows', route: '/list', onClick: () =>  '' },
    { title: 'Profile', route: '/user', onClick: () =>  '' },
    { title: 'LogOut', route: '/logout', onClick: () => this.logout() },
  ]

  constructor(private tokenStorageService: TokenStorageService) {}

  logout(): void {
    this.isLoggedIn = false;
    this.tokenStorageService.logout();
    this.logoutEvent.emit(true);
  }

}
