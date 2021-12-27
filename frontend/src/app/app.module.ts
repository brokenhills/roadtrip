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

@NgModule({
  declarations: [
    AppComponent,
    RecentWorkflowsComponent,
    CreateWorkflowComponent,
    LoginComponent,
    ListWorkflowComponent,
    UserProfileComponent,
    EditWorkflowComponent
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
