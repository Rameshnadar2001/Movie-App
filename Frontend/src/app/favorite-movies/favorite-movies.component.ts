import { Component, Input, OnInit } from '@angular/core';
import { UserServiceService } from '../services/user-service.service';
import { AuthServiceService } from '../services/auth-service.service';
import { Movie } from '../models/Movie';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-favorite-movies',
  templateUrl: './favorite-movies.component.html',
  styleUrls: ['./favorite-movies.component.css'],
})
export class FavoriteMoviesComponent implements OnInit {
  constructor(
    private userService: UserServiceService,
    private authService: AuthServiceService,
    private snackBar: MatSnackBar
  ) {}
  favoriteMovies: any[] = [];
  isFavoriteMovie: boolean = true;
  movie?: Movie;
  removedMovie?: any;
  length: any;
  isFav: boolean = false;

  ngOnInit(): void {
    this.getFavoriteMovies();
    this.isFav = true;
  }

  getFavoriteMovies() {
    this.userService.getFavoriteMovies(this.authService.token).subscribe({
      next: (data) => {
        this.favoriteMovies = data.body;
        this.length = this.favoriteMovies ? this.favoriteMovies.length : 0;
      },
      error: (error) => {
        alert(error.error);
      },
    });
  }

  deleteMovie($event: any) {
    this.userService.deleteMovieFromFavorite($event).subscribe({
      next: (data) => {
        this.removedMovie = data.body;
        this.favoriteMovies = this.removedMovie.favoriteMovieList;
        this.length = this.favoriteMovies ? this.favoriteMovies.length : 0;
        this.snackBar.open('Removed from favorites', 'success', {
          duration: 3000,
          panelClass: ['mat-toolbar', 'mat-primary'],
        });
      },
      error: (error) => {
        alert(error.error);
      },
    });
  }
}
