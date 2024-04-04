import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { UserServiceService } from '../services/user-service.service';
import { AuthServiceService } from '../services/auth-service.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { FavoriteMoviesComponent } from '../favorite-movies/favorite-movies.component';
import { Movie } from '../models/Movie';
import { RouterServiceService } from '../services/router-service.service';
import { MovieRecommendationService } from '../services/movie-recommendation.service';

@Component({
  selector: 'app-movie-card',
  templateUrl: './movie-card.component.html',
  styleUrls: ['./movie-card.component.css'],
})
export class MovieCardComponent implements OnInit {
  @Input() movie?: any;
  @Input() genresss: any[] = [];
  @Input() isFavoriteMovie?: boolean;
  @Output() onDelete: EventEmitter<Movie> = new EventEmitter<Movie>();
  movieGenres: string[] = [];
  isHovered: boolean = false;
  movieId: any;
  favoriteMovies: any[] = [];
  genre_names: string = '';
  response: boolean = false;
  videoKey: any;

  constructor(
    private userService: UserServiceService,
    private authService: AuthServiceService,
    private snackBar: MatSnackBar,
    private routerService: RouterServiceService,
    private movieService: MovieRecommendationService
  ) {}

  async ngOnInit(): Promise<void> {
    for (let genre of this.genresss) {
      for (let g of this.movie.genre_ids) {
        if (g == genre.id) {
          this.movieGenres.push(genre.name);
        }
      }
    }
    this.genre_names = this.movieGenres.join(',');

    if (this.authService.isLoggedIn) {
      if (this.userService.favoriteMovies) {
        this.getFavoriteMovies();
      }
    }
    this.videoKey = await this.userService.getVideo(this.movie.id);
  }

  addToFavorites(movie: any) {
    if (!this.authService.isLoggedIn) {
      this.response = confirm('You need to first to login to add favorites');
      if (this.response) {
        this.routerService.navigateToLogin();
      } else {
        this.routerService.navigateToLandingView();
      }
    } else {
      this.userService.addToFavoriteMovies(movie).subscribe({
        next: (data) => {
          this.snackBar.open('Added to favorites', 'success', {
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

  deleteFromFavorite() {
    this.movieId = this.movie.id;
    this.onDelete.emit(this.movieId);
  }

  buttonVisibility() {
    this.isHovered = true;
  }
  onMouseLeave() {
    this.isHovered = false;
  }

  getFavoriteMovies() {
    this.userService.getFavoriteMovies(this.authService.token).subscribe({
      next: (data) => {
        this.userService.favoriteMovies = data.body;
        this.favoriteMovies = data.body;
      },
      error: (error) => {
        alert(error.error);
      },
    });
  }

  isAFavoriteMovie(movie: any): boolean {
    if (this.favoriteMovies) {
      return this.favoriteMovies.some((favMovie) => favMovie.id === movie.id);
    } else {
      return false;
    }
  }

  watchVideo() {
    this.movieService.openVideo(this.videoKey);
  }
}
