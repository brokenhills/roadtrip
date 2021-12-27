import { Component, OnInit } from '@angular/core';
import { ApiService } from '../api.service';
import { TokenStorageService } from '../token-storage.service';

@Component({
  selector: 'app-recent-workflows',
  templateUrl: './recent-workflows.component.html',
  styleUrls: ['./recent-workflows.component.sass']
})
export class RecentWorkflowsComponent implements OnInit {

  workflows: any;
  isLoggedIn: boolean = false;

  constructor(private api: ApiService, private tokenStorageService: TokenStorageService) { }

  ngOnInit(): void {
    this.isLoggedIn = !!this.tokenStorageService.getToken();
    if (this.isLoggedIn) {
      this.api.getRecentWorkflows().subscribe(data => this.workflows = data._embedded.workflows);
    } 
  }
}
