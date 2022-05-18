import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { CreateProjectComponent } from './create-project/create-project.component';
import { CreateWorkflowComponent } from './create-workflow/create-workflow.component';
import { EditProjectComponent } from './edit-project/edit-project.component';
import { EditWorkflowComponent } from './edit-workflow/edit-workflow.component';
import { ListProjectComponent } from './list-project/list-project.component';
import { ListWorkflowComponent } from './list-workflow/list-workflow.component';
import { RecentWorkflowsComponent } from './recent-workflows/recent-workflows.component';
import { UserProfileComponent } from './user-profile/user-profile.component';

const routes: Routes = [
  {
    path: 'logout',
    redirectTo: '/',
  },
  {
    path:  'home',
    component:  RecentWorkflowsComponent,
  },
  {
    path:  'list',
    component:  ListWorkflowComponent,
    children: [
      {
        path: 'create',
        component: CreateWorkflowComponent,
      },
      {
        path: 'edit',
        component: EditWorkflowComponent,
      },
    ]
  },
  {
    path:  'projects',
    component:  ListProjectComponent,
    children: [
      {
        path: 'create',
        component: CreateProjectComponent,
      },
      {
        path: 'edit',
        component: EditProjectComponent,
      },
    ]
  },
  {
    path:  'user',
    component:  UserProfileComponent,
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
