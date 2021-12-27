import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ApiService } from '../api.service';
import { TokenStorageService } from '../token-storage.service';

@Component({
  selector: 'app-list-workflow',
  templateUrl: './list-workflow.component.html',
  styleUrls: ['./list-workflow.component.sass']
})
export class ListWorkflowComponent implements OnInit {

  workflows: any;
  isShowList: boolean = true;
  showEditForm: boolean = false;
  showCreateForm: boolean = false;
  workflowUrl: string = '';
  currentUserUrl: string = '';

  constructor(private api: ApiService, private token: TokenStorageService, private router: Router) { }

  ngOnInit(): void {
    this.router.navigate(['/list']);
    this.api.getWorkflows().subscribe(data => this.workflows = data._embedded.workflows);
  }

  onEditWorkflow(workflow: any) {
    this.isShowList = false;
    this.workflowUrl = workflow._links.self.href;
    this.showEditForm = true;
  }

  onCreateWorkflow() {
    this.api.getUser(this.token.getUser().username).subscribe(data => {
      this.currentUserUrl = data._links.self.href;
      this.isShowList = false;
      this.showCreateForm = true;
    });
  }

  onApproveEdit(event: boolean) {
    this.showEditForm = !event;
    this.isShowList = true;
  }

  onCancelEdit(event: boolean) {
    this.showEditForm = !event;
    this.isShowList = true;
  }

  onApproveCreate(event: boolean) {
    this.showCreateForm = !event;
    this.isShowList = true;
  }

  onCancelCreate(event: boolean) {
    this.showCreateForm = !event;
    this.isShowList = true;
  }

}
