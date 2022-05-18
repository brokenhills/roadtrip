import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { RecentWorkflowsComponent } from './recent-workflows/recent-workflows.component';
import { CreateWorkflowComponent } from './create-workflow/create-workflow.component';
import { LoginComponent } from './login/login.component';
import { ListWorkflowComponent } from './list-workflow/list-workflow.component';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { UserProfileComponent } from './user-profile/user-profile.component';
import { authInterceptorProviders } from './auth.interceptor';
import { EditWorkflowComponent } from './edit-workflow/edit-workflow.component';
import { TopBarComponent } from './top-bar/top-bar.component';
import { ListProjectComponent } from './list-project/list-project.component';
import { CreateProjectComponent } from './create-project/create-project.component';
import { EditProjectComponent } from './edit-project/edit-project.component';

@NgModule({
  declarations: [
    AppComponent,
    RecentWorkflowsComponent,
    CreateWorkflowComponent,
    LoginComponent,
    ListWorkflowComponent,
    UserProfileComponent,
    EditWorkflowComponent,
    TopBarComponent,
    ListProjectComponent,
    CreateProjectComponent,
    EditProjectComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule
  ],
  providers: [authInterceptorProviders],
  bootstrap: [AppComponent]
})
export class AppModule { }
