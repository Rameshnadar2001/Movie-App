import { Component, Input, OnInit } from '@angular/core';
import { UserServiceService } from '../services/user-service.service';
import { AuthServiceService } from '../services/auth-service.service';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-movie-recommendation',
  templateUrl: './movie-recommendation.component.html',
  styleUrls: ['./movie-recommendation.component.css'],
})
export class MovieRecommendationComponent implements OnInit {
  @Input() recommededMovie: any;
  constructor(
    private userService: UserServiceService,
    private authService: AuthServiceService,
    private snackBar: MatSnackBar
  ) {}

  ngOnInit(): void {}

  addToFavorites(movie: any) {
    this.userService.addToFavoriteMovies(movie).subscribe({
      next: (data) => {
        this.snackBar.open('Added to favorites', 'success', {
          duration: 3000,
          panelClass: ['mat-toolbar', 'mat-primary'],
        });
      },
      error: (error) => {
        alert(error.error)
      },
    });
  }
}
