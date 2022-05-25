import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormArray, FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { ApiService } from '../api.service';

@Component({
  selector: 'app-edit-project',
  templateUrl: './edit-project.component.html',
  styleUrls: ['./edit-project.component.sass']
})
export class EditProjectComponent implements OnInit {

  @Input()
  projectUrl: string = '';

  @Output()
  saveEvent: EventEmitter<boolean> = new EventEmitter();
  
  @Output()
  cancelEvent: EventEmitter<boolean> = new EventEmitter();

  form: FormGroup = new FormGroup({});
  
  project: any;

  workflows: Array<any> = [];

  departments: Array<any> = [];

  constructor(private formBuilder: FormBuilder, private apiService: ApiService) { }

  ngOnInit(): void {
    this.form = this.formBuilder.group({
      name: new FormControl(null, Validators.required),
      description: new FormControl(null, Validators.required),
      dateFrom: new FormControl(null, Validators.required),
      dateTo: new FormControl(null, Validators.required),
      department: new FormControl(null),
      workflows: new FormArray([]),
    });
    this.apiService.getAny(this.projectUrl).subscribe((data) => {
      this.project = data;
      if (this.project) {
        this.form.get('name')?.setValue(this.project.name);
        this.form.get('description')?.setValue(this.project.description);
        this.form.get('dateFrom')?.setValue(this.project.dateFrom);
        this.form.get('dateTo')?.setValue(this.project.dateTo);
        this.form.get('department')?.setValue(this.project.department);
        this.form.get('workflows')?.setValue(this.workflows);
        this.apiService.getAny(this.project._links.workflows.href).subscribe((data) => {
          this.workflows = data;
          this.form.controls['workflows'].setValue(this.workflows);
        });
      }
    })
  }

  onDepartmentSearch(event: any) {
    this.apiService.searchDepartment(event.target.value).subscribe((data) => {
      this.departments = data._embedded.departments;
    });
  }

  onSubmit() {
    this.apiService.putAny(this.projectUrl, this.form.value)
      .subscribe(() => this.saveEvent.emit(true));
  }

  onCancel() {
    this.cancelEvent.emit(true);
  }

}
