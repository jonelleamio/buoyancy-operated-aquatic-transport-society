import {Component} from '@angular/core';
import {FormControl, FormGroup, ReactiveFormsModule} from '@angular/forms';
import {AuthService} from '../../../in/apis/auth.service';
import {Router} from '@angular/router';
import {MatCard, MatCardTitle} from '@angular/material/card';
import {MatButton} from '@angular/material/button';
import {MatFormField, MatInput} from '@angular/material/input';

type LoginReqFormGroup = { username: FormControl<string>; password: FormControl<string> };

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  imports: [
    MatCard,
    MatCardTitle,
    MatFormField,
    MatButton,
    ReactiveFormsModule,
    MatInput,
    MatFormField
  ]
})
export class LoginComponent {
  form: FormGroup<LoginReqFormGroup> = new FormGroup({
    username: new FormControl('', { nonNullable: true }),
    password: new FormControl('', { nonNullable: true }),
  });

  constructor(private auth: AuthService, private router: Router) {}

  submit() {
    const { username, password } = this.form.value;
    this.auth.login({username:username!, password:password!}).subscribe(() => {
      this.router.navigateByUrl('/boats');
    });
  }
}
