import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { ApiService } from '../api.service';
import { TokenStorageService } from '../token-storage.service';

@Component({
  selector: 'app-edit-workflow',
  templateUrl: './edit-workflow.component.html',
  styleUrls: ['./edit-workflow.component.sass']
})
export class EditWorkflowComponent implements OnInit {

  @Input()
  workflowUrl: string = '';
  @Output()
  saveEvent: EventEmitter<boolean> = new EventEmitter();
  @Output()
  cancelEvent: EventEmitter<boolean> = new EventEmitter();
  workflow: any;
  form: FormGroup = new FormGroup({});
  workflowTypes: Array<string> = [
    'TASK',
    'REQUIREMENT',
    'ISSUE',
    'BUG'
  ];
  workflowStates: Array<string> = [
    'ACTIVE',
    'CLOSED',
  ]

  workflowFiles: Array<any> = [];
  files: Array<File> = [];

  workflowId: string = '';

  constructor(private tokenService: TokenStorageService, private api: ApiService, private fb: FormBuilder, private router: Router) { }

  ngOnInit(): void {
    this.form = this.fb.group({
      name: new FormControl(null, Validators.required),
      type: new FormControl(null, Validators.required),
      state: new FormControl(null, Validators.required),
      content: new FormControl(null, Validators.required),
      parent: new FormControl(null),
      child: new FormControl(null),
    });
    this.api.getAny(this.workflowUrl).subscribe(data => {
      this.workflow = data;
      if (this.workflow) {
        this.workflowId = this.workflowUrl.split("/").pop() || '';
        this.form.get('name')?.setValue(this.workflow.name);
        this.form.get('type')?.setValue(this.workflow.type);
        this.form.get('state')?.setValue(this.workflow.state);
        this.form.get('content')?.setValue(this.workflow.content);
        this.form.get('parent')?.setValue(this.workflow.parent);
        this.form.get('child')?.setValue(this.workflow.child);
        if (this.workflowId) {
          this.api.getFiles(this.workflowId).subscribe(
            (data) => this.workflowFiles = data,
            (error) => console.log(error)
          );
        }
        
      }
    })
  }

  onFileSelected(event: any) {
    const file: File = event.target.files[0];
    if (file) {
      this.files.push(file);
    }
    console.log(this.files);
  }

  uploadFiles(workflowId: string) {
    this.files.forEach(file => this.api.upload(workflowId, file).subscribe(
      (data) => console.log(data),
      (error) => console.log(error)
    ));
  }

  onRemove(index: number) {
    this.files.splice(index, 1);
    console.log(this.files);
  }

  onRemoveWorkflowFile(id: string) {
    this.api.deleteFile(id).subscribe(
      (data) => console.log(data),
      (error) => console.log(error)
    );
  }

  onSubmit() {
    this.api.putAny(this.workflowUrl, this.form.value)
      .subscribe(
        (data) => this.uploadFiles(this.workflowId),
        (error) => console.log(error)
      );
    this.saveEvent.emit(true);
    window.location.reload();
  }

  onCancel() {
    this.cancelEvent.emit(true);
    window.location.reload();
  }
}
