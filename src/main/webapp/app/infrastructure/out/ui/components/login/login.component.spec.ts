import { ComponentFixture, ComponentFixtureAutoDetect, TestBed } from '@angular/core/testing';

import { By } from '@angular/platform-browser';
import {LoginComponent} from './login.component';

describe('LoginComponent', () => {
  let fixture: ComponentFixture<LoginComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      providers: [{ provide: ComponentFixtureAutoDetect, useValue: true }],
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(LoginComponent);
  });

  it('should verify username is not empty', () => {
    const usernameInput = fixture.debugElement.query(By.css('#username')).nativeElement as HTMLInputElement;
    usernameInput.value = 'testuser';
    usernameInput.dispatchEvent(new Event('input'));

    expect(fixture.componentInstance.form.get('username')?.value).toBe('testuser');
  });

  it('should verify password is not empty', () => {
    const passwordInput = fixture.debugElement.query(By.css('#password')).nativeElement as HTMLInputElement;
    passwordInput.value = 'testpassword';
    passwordInput.dispatchEvent(new Event('input'));

    expect(fixture.componentInstance.form.get('password')?.value).toBe('testpassword');
  });

  it('should log in on click on login button when form is valid', () => {
    const loginButton = fixture.debugElement.query(By.css('#btn-login')).nativeElement as HTMLButtonElement;
    const usernameInput = fixture.debugElement.query(By.css('#username')).nativeElement as HTMLInputElement;
    const passwordInput = fixture.debugElement.query(By.css('#password')).nativeElement as HTMLInputElement;

    usernameInput.value = 'testuser';
    passwordInput.value = 'testpassword';
    usernameInput.dispatchEvent(new Event('input'));
    passwordInput.dispatchEvent(new Event('input'));

    loginButton.click();

    expect(fixture.componentInstance.form.valid).toBe(true);
  })
});
