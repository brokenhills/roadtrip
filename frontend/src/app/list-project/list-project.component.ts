import { Component, OnInit } from '@angular/core';
import { environment } from 'src/environments/environment';
import { ApiService } from '../api.service';

@Component({
  selector: 'app-list-project',
  templateUrl: './list-project.component.html',
  styleUrls: ['./list-project.component.sass']
})
export class ListProjectComponent implements OnInit {

  projects: any;
  API_URL: string = environment.apiUrl;
  isShowList: boolean = true;
  showCreateForm: boolean = false;
  showEditForm: boolean = false;
  projectUrl: string = '';

  constructor(private apiService: ApiService) { }

  ngOnInit(): void {
    this.updateProjectList();
  }

  updateProjectList(): void {
    this.apiService.getAny(`${this.API_URL}/projects`).subscribe(
      (data) => this.projects = data._embedded.projects
    )
  }

  onEditProject(project: any) {
    this.isShowList = false;
    this.projectUrl = project._links.self.href;
    this.showEditForm = true;
  }

  onCreateProject(): void {
    this.isShowList = false;
    this.showCreateForm = true;
  }

  onApproveSave(event: boolean): void {
    this.showCreateForm = !event;
    this.isShowList = true;
    this.updateProjectList();
  }

  onCancelSave(event: boolean): void {
    this.showCreateForm = !event;
    this.isShowList = true;
    this.updateProjectList();
  }

  onApproveEdit(event: boolean): void {
    this.showEditForm = !event;
    this.isShowList = true;
    this.updateProjectList();
  }

  onCancelEdit(event: boolean): void {
    this.showEditForm = !event;
    this.isShowList = true;
    this.updateProjectList();
  }

}
