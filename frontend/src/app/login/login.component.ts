import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { ApiService } from '../api.service';
import { TokenStorageService } from '../token-storage.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.sass']
})
export class LoginComponent implements OnInit {

  result: any;
  form: FormGroup = new FormGroup({});
  isLoggedIn = false;
  isLoginFailed = false;
  errorMessage = '';
  role: string = '';
  isEmptyUsername = false;
  isEmptyPassword = false;

  constructor(
    private fb: FormBuilder,
    private route: ActivatedRoute,
    private router: Router,
    private api: ApiService,
    private tokenStorage: TokenStorageService) { }

  ngOnInit(): void {
    this.form = this.fb.group({
      username: new FormControl(null, [Validators.required, Validators.minLength(3)]),
      password: new FormControl(null, [Validators.required, Validators.minLength(6)])
    });
    if (this.tokenStorage.getToken()) {
      this.isLoggedIn = true;
      this.role = this.tokenStorage.getUser().role;
    }
  }

  get username() {
    return this.form.get('username');
  }

  get password() {
    return this.form.get('password');
  }

  onSubmit() {
    this.isEmptyUsername = this.username?.value ? false : true;
    this.isEmptyPassword = this.password?.value ? false : true;
    if (this.form.valid) {
      this.api.login({ username: this.username?.value, password: this.password?.value })
      .subscribe(data => {
        this.tokenStorage.saveToken(data.token);
        this.tokenStorage.saveUser(data.user);
        this.isLoginFailed = false;
        this.isLoggedIn = true;
        this.role = this.tokenStorage.getUser().role;
        this.reloadPage();
      },
      err => {
        this.errorMessage = err.error.message || err.message;
        this.isLoginFailed = true;
      });
    }
  }

  reloadPage(): void {
    window.location.reload();
  }
}
