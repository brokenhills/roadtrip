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
  API_URL = environment.apiUrl;
  isShowList = true;
  showCreateForm = false;

  constructor(private apiService: ApiService) { }

  ngOnInit(): void {
    this.updateProjectList();
  }

  updateProjectList(): void {
    this.apiService.getAny(`${this.API_URL}/projects`).subscribe(
      (data) => this.projects = data._embedded.projects
    )
  }

  onCreateProject(): void {
    this.isShowList = false;
    this.showCreateForm = true;
  }

  onApproveSave(event: boolean) {
    this.showCreateForm = !event;
    this.isShowList = true;
    this.updateProjectList();
  }

  onCancelSave(event: boolean) {
    this.showCreateForm = !event;
    this.isShowList = true;
    this.updateProjectList();
  }

}
