import { Component, Input } from '@angular/core';
import { User } from '../models/User';
import { UserServiceService } from '../services/user-service.service';
import { AuthServiceService } from '../services/auth-service.service';
import { AbstractControl, FormBuilder, Validators } from '@angular/forms';
import { RouterServiceService } from '../services/router-service.service';

@Component({
  selector: 'app-resgistration-form',
  templateUrl: './resgistration-form.component.html',
  styleUrls: ['./resgistration-form.component.css'],
})
export class ResgistrationFormComponent {
  user: User = {};
  token: any;
  @Input() isPresentInSignup: boolean = true;
  hide = true;
  registeredStatus = false;
  errorMessage = false;
  errorText: string = '';

  constructor(
    private userService: UserServiceService,
    private authService: AuthServiceService,
    private routerService: RouterServiceService,
    private fb: FormBuilder
  ) {}

  registerForm = this.fb.group({
    userName: [
      '',
      [Validators.required, Validators.minLength(2), Validators.maxLength(20)],
    ],
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
    age: ['', [Validators.required]],
    image: [null, Validators.required],
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

  get userName() {
    return this.registerForm.get('userName');
  }

  get emailId() {
    return this.registerForm.get('emailId');
  }

  get password() {
    return this.registerForm.get('password');
  }

  get confirmPassword() {
    return this.registerForm.get('confirmPassword');
  }

  get age() {
    return this.registerForm.get('age');
  }

  get image() {
    return this.registerForm.get('image');
  }

  onImageChange(event: any) {
    const profileImage = event.target.files[0];
    this.user.image = profileImage;
    this.registerForm.get('image')!.setValue(profileImage);
  }

  canDeactivate() {
    if (!this.registeredStatus) {
      this.registeredStatus = confirm(
        'You have not registered. Are you sure, you want to leave?'
      );
    }
    return this.registeredStatus;
  }

  register() {
    if (this.registerForm.valid) {
      const formData: FormData = new FormData();
      formData.append(
        'userName',
        this.registerForm.get('userName')!.value ?? ''
      );
      formData.append('emailId', this.registerForm.get('emailId')!.value ?? '');
      formData.append(
        'password',
        this.registerForm.get('password')!.value ?? ''
      );
      formData.append('age', this.registerForm.get('age')!.value ?? '');
      if (this.user.image) {
        formData.append('image', this.user.image, this.user.image.name);
      }

      this.userService.registerUser(formData).subscribe({
        next: (data) => {
          this.registeredStatus = true;
          this.errorMessage = false;
        },
        error: (error) => {
          this.errorText = error.error;
          this.errorMessage = true;
        },
      });
    }
  }
}
