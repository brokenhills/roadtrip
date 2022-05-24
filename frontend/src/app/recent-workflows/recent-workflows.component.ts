import { Component, OnInit } from '@angular/core';
import { environment } from 'src/environments/environment';
import { ApiService } from '../api.service';
import { TokenStorageService } from '../token-storage.service';

@Component({
  selector: 'app-recent-workflows',
  templateUrl: './recent-workflows.component.html',
  styleUrls: ['./recent-workflows.component.sass']
})
export class RecentWorkflowsComponent implements OnInit {

  API_URL = environment.apiUrl;

  states: Array<string> = [ 'NEW', 'ACTIVE', 'CLOSED' ];
  workflows: any;
  isLoggedIn: boolean = false;
  showEditForm: boolean = false;
  workflowUrl: string = '';

  constructor(private api: ApiService, private tokenStorage: TokenStorageService) { }

  ngOnInit(): void {
    this.isLoggedIn = !!this.tokenStorage.getToken();
    this.getRecentWorkflows();
  }

  getRecentWorkflows(): void {
    if (this.isLoggedIn) {
      this.api.getRecentWorkflows(this.tokenStorage.getUser().id).subscribe(data => this.workflows = data);
    }
  }

  onEditWorkflow(url: string) {
    this.showEditForm = true;
    this.workflowUrl = url;
  }

  onApproveEdit(event: boolean) {
    this.showEditForm = !event;
    this.getRecentWorkflows();
  }

  onCancelEdit(event: boolean) {
    this.showEditForm = !event;
    this.getRecentWorkflows();
  }
}
