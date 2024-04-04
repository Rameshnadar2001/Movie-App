import { Component } from '@angular/core';
import { User } from '../models/User';
import { AuthServiceService } from '../services/auth-service.service';
import { UserServiceService } from '../services/user-service.service';


@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
})
export class LoginComponent {
  user: User = {};
  token: any;
  hide = true;
  isPresentInLogin: boolean = true;
  errorMessage=false;
  errorText = '';

  constructor(
    private userService: UserServiceService,
    private authService: AuthServiceService
  ) {}

  getToken() {
    this.userService.generateToken(this.user).subscribe({
      next: (data) => {
        this.token = data;
        this.authService.login(this.token);
      },
      error: (error) => {
        this.errorText = error.error;
        this.errorMessage = true;
      },
    });
  }
} 
