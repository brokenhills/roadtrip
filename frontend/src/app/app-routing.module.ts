import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { CreateWorkflowComponent } from './create-workflow/create-workflow.component';
import { EditWorkflowComponent } from './edit-workflow/edit-workflow.component';
import { ListWorkflowComponent } from './list-workflow/list-workflow.component';
import { LoginComponent } from './login/login.component';
import { RecentWorkflowsComponent } from './recent-workflows/recent-workflows.component';
import { UserProfileComponent } from './user-profile/user-profile.component';

const routes: Routes = [
  {
    path:  '',
    redirectTo:  'home',
    pathMatch:  'full',
  },
  {
    path:  'login',
    component:  LoginComponent,
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
    path:  'user',
    component:  UserProfileComponent,
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
