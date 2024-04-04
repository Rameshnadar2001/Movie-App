import { Injectable } from '@angular/core';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root',
})
export class RouterServiceService {
  constructor(private router: Router) {}

  navigateToLandingView() {
    this.router.navigate(['home']);
  }

  navigateToLogin() {
    this.router.navigate(['login']);
  }

  navigateToFavorites() {
    this.router.navigate(['favoriteMoves']);
  }

  navigateToHome() {
    this.router.navigate(['user/home']);
  }
}
