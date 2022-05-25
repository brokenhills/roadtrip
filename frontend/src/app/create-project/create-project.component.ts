import { Component, OnInit, Output, EventEmitter } from '@angular/core';
import { FormArray, FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { ApiService } from '../api.service';

@Component({
  selector: 'app-create-project',
  templateUrl: './create-project.component.html',
  styleUrls: ['./create-project.component.sass']
})
export class CreateProjectComponent implements OnInit {

  @Output()
  saveEvent: EventEmitter<boolean> = new EventEmitter();

  @Output()
  cancelEvent: EventEmitter<boolean> = new EventEmitter();

  form: FormGroup = new FormGroup({});

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
  }

  onSubmit(): void {
    this.apiService.createProject(this.form.value).subscribe(() => {
        this.saveEvent.emit(true);
      }
    )
  }

  onCancel() {
    this.cancelEvent.emit(true);
  }
}
