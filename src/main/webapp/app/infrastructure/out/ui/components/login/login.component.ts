import {Component} from '@angular/core';
import {FormControl, FormGroup, ReactiveFormsModule} from '@angular/forms';
import {Router} from '@angular/router';
import {MatCard, MatCardTitle} from '@angular/material/card';
import {MatButton} from '@angular/material/button';
import {MatInput} from '@angular/material/input';
import {MatFormFieldModule} from '@angular/material/form-field'
import {AuthService} from '../../../../in/apis/auth.service';

type LoginReqFormGroup = { username: FormControl<string>; password: FormControl<string> };

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss',
  imports: [
    MatCard,
    MatCardTitle,
    MatFormFieldModule,
    MatButton,
    ReactiveFormsModule,
    MatInput,
  ]
})
export class LoginComponent {
  form: FormGroup<LoginReqFormGroup> = new FormGroup({
    username: new FormControl('root', {nonNullable: true}),
    password: new FormControl('root', {nonNullable: true}),
  });

  constructor(private auth: AuthService, private router: Router) {
  }

  submit() {
    const {username, password} = this.form.value;
    this.auth.login({username: username!, password: password!}).subscribe(() => {
      this.router.navigateByUrl('/boats');
    });
  }
}
