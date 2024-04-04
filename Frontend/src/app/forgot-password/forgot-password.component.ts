import { Component, Input, OnInit } from '@angular/core';
import { UserServiceService } from '../services/user-service.service';
import { AuthServiceService } from '../services/auth-service.service';
import { RouterServiceService } from '../services/router-service.service';
import { AbstractControl, FormBuilder, Validators } from '@angular/forms';

@Component({
  selector: 'app-forgot-password',
  templateUrl: './forgot-password.component.html',
  styleUrls: ['./forgot-password.component.css'],
})
export class ForgotPasswordComponent {
  @Input() isPresentInSignup: boolean = true;
  errorMessage = false;
  errorText = '';
  hide = true;
  constructor(
    private userService: UserServiceService,
    private authService: AuthServiceService,
    private routerService: RouterServiceService,
    private fb: FormBuilder
  ) {}

  updateForm = this.fb.group({
    emailId: [
      '',
      [
        Validators.required,
        Validators.pattern(
          /^(([^<>()[\]\.,;:\s@\"]+(\.[^<>()[\]\.,;:\s@\"]+)*)|(\".+\"))@(([^<>()[\]\.,;:\s@\"]+\.)+[^<>()[\]\.,;:\s@\"]{2,})$/i
        ),
      ],
    ],
    password: [
      '',
      [
        Validators.required,
        Validators.pattern(
          /^(?=.*\d)(?=.*[!@#$%^&*])(?=.*[a-z])(?=.*[A-Z]).{8,}$/
        ),
      ],
    ],
    confirmPassword: ['', [Validators.required, this.passwordMatchValidator]],
  });

  passwordMatchValidator(
    control: AbstractControl
  ): { [key: string]: boolean } | null {
    const password = control.root.get('password');
    const confirmPassword = control.value;
    // Return null if the passwords match
    if (password && confirmPassword === password.value) {
      return null;
    }
    // Return an error object if the passwords don't match
    return { passwordMismatch: true };
  }

  get emailId() {
    return this.updateForm.get('emailId');
  }

  get password() {
    return this.updateForm.get('password');
  }

  get confirmPassword() {
    return this.updateForm.get('confirmPassword');
  }

  update() {
    if (this.updateForm.valid) {
      this.userService.updateUser(this.updateForm.value).subscribe({
        next: (data) => {
          alert('Password updated Successfully!');
          this.routerService.navigateToLogin();
        },
        error: (error) => {
          this.errorText = error.error;
          this.errorMessage = true;
        },
      });
    } else {
      alert('Enter valid details!');
    }
  }
}
