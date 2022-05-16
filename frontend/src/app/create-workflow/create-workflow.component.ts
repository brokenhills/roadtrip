import { Component, OnInit, Output, EventEmitter, Input } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { ApiService } from '../api.service';
import { TokenStorageService } from '../token-storage.service';

@Component({
  selector: 'app-create-workflow',
  templateUrl: './create-workflow.component.html',
  styleUrls: ['./create-workflow.component.sass']
})
export class CreateWorkflowComponent implements OnInit {

  @Input()
  currentUserUrl: string = '';
  @Output()
  cancelEvent: EventEmitter<boolean> = new EventEmitter();
  @Output()
  saveEvent: EventEmitter<boolean> = new EventEmitter();
  
  form: FormGroup = new FormGroup({});
  isLoggedIn: boolean = false;
  role: string = '';
  userId: string = '';
  workflowTypes: Array<string> = [
    'TASK',
    'REQUIREMENT',
    'ISSUE',
    'BUG'
  ];
  workflowStates: Array<string> = [
    'NEW',
  ]
  workflows: any;

  constructor(
    private fb: FormBuilder, 
    private tokenStorage: TokenStorageService,
    private api: ApiService) { }

  ngOnInit(): void {
    this.form = this.fb.group({
      name: new FormControl(null, Validators.required),
      type: new FormControl(null, Validators.required),
      state: new FormControl(null, Validators.required),
      content: new FormControl(null, Validators.required),
      parent: new FormControl(null),
      child: new FormControl(null),
      user: new FormControl(this.currentUserUrl, Validators.required),
    });
    if (this.tokenStorage.getToken()) {
      this.isLoggedIn = true;
      this.role = this.tokenStorage.getUser().role;
    }
  }

  onSubmit() {
    this.api.createWorkflow(this.form.value).subscribe(() => {
      this.saveEvent.emit(true);
    });

  }

  onCancel() {
    this.cancelEvent.emit(true);
  }

}
