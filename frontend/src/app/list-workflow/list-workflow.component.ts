import { Component, Input, OnInit } from '@angular/core';
import { ApiService } from '../api.service';
import { TokenStorageService } from '../token-storage.service';

@Component({
  selector: 'app-list-workflow',
  templateUrl: './list-workflow.component.html',
  styleUrls: ['./list-workflow.component.sass'],
})
export class ListWorkflowComponent implements OnInit {

  @Input()
  workflows: any;
  isShowList: boolean = true;
  showEditForm: boolean = false;
  showCreateForm: boolean = false;
  workflowUrl: string = '';
  currentUserUrl: string = '';

  constructor(private api: ApiService, private tokenStorage: TokenStorageService) { }

  ngOnInit(): void {
    this.isShowList = !!this.tokenStorage.getToken();
    if (this.isShowList) {
      this.updateWorkflowList();
    }
  }

  updateWorkflowList(): void {
    this.api.getWorkflows().subscribe(data => this.workflows = data._embedded.workflows);
  }

  onEditWorkflow(workflow: any) {
    this.isShowList = false;
    this.workflowUrl = workflow._links.self.href;
    this.showEditForm = true;
  }

  onCreateWorkflow() {
    this.api.getUser(this.tokenStorage.getUser().username).subscribe(data => {
      this.currentUserUrl = data._links.self.href;
      this.isShowList = false;
      this.showCreateForm = true;
    });
  }

  onApproveEdit(event: boolean) {
    this.showEditForm = !event;
    this.isShowList = true;
    this.updateWorkflowList();
  }

  onCancelEdit(event: boolean) {
    this.showEditForm = !event;
    this.isShowList = true;
    this.updateWorkflowList();
  }

  onApproveCreate(event: boolean) {
    this.showCreateForm = !event;
    this.isShowList = true;
    this.updateWorkflowList();
  }

  onUpdateWorkflows(event: any) {
    this.workflows = event;
  }

  onCancelCreate(event: boolean) {
    this.showCreateForm = !event;
    this.isShowList = true;
    this.updateWorkflowList();
  }

}
