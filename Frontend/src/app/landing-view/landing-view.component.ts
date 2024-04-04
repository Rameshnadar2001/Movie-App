import { Component, HostListener, OnInit } from '@angular/core';
import { MovieRecommendationService } from '../services/movie-recommendation.service';
import { AuthServiceService } from '../services/auth-service.service';
import { UserServiceService } from '../services/user-service.service';
import { RouterServiceService } from '../services/router-service.service';

@Component({
  selector: 'app-landing-view',
  templateUrl: './landing-view.component.html',
  styleUrls: ['./landing-view.component.css'],
})
export class LandingViewComponent implements OnInit {
  mov: any;
  movies: any[] = [];
  newMovies: any[] = [];
  genres: any[] = [];
  movieSearch: string = '';
  langByChip: string = '';
  currentPage: number = 0;
  nextPage: number = 0;
  prevPage: number = 0;
  totalPage: number = 0;
  genreSelection: string = '';
  isScrolledDown: boolean = false;
  trendingMovies: any[] = [];
  now_playingMovies: any[] = [];
  searchedMovies: any[] = [];

  constructor(
    private movieService: MovieRecommendationService,
    private authService: AuthServiceService,
    private userService: UserServiceService,
    private routerService: RouterServiceService
  ) {}

  ngOnInit(): void {
    this.getAllMovies();
    this.getTrendingMovies();
    this.getNow_playingMovies();
    if (this.authService.isLoggedIn) {
      this.routerService.navigateToHome();
    } else {
      this.routerService.navigateToLandingView();
    }
  }

  scrollToTop() {
    // Scroll to the top of the page
    window.scrollTo({ top: 0, behavior: 'smooth' });
  }

  sortByGenre() {
    if (this.langByChip.length) {
      if (this.genreSelection.length) {
        //for specific genres and Lang
        this.movieService
          .getMoviesByGenreAndLang(this.langByChip, this.genreSelection)
          .subscribe({
            next: (data) => {
              this.movies = data.results;
              this.currentPage = data.page;
              this.nextPage = this.currentPage + 1;
              this.totalPage = data.total_pages;
            },
          });
      } else {
        //for ALL Genres
        this.movieService.getMoviesByLang(this.langByChip).subscribe({
          next: (data) => {
            this.movies = data.results;
            this.currentPage = data.page;
            this.nextPage = this.currentPage + 1;
            this.totalPage = data.total_pages;
          },
        });
      }
    } else if (this.genreSelection.length) {
      //for All languages it sorts by genre
      this.movieService.getMoviesByGenre(this.genreSelection).subscribe({
        next: (data) => {
          this.movies = data.results;
          this.currentPage = data.page;
          this.nextPage = this.currentPage + 1;
          this.totalPage = data.total_pages;
        },
      });
    } else {
      this.getAllMovies();
    }
  }

  getAllMovies() {
    this.movieService.getMovies().subscribe({
      next: (data) => {
        this.mov = data;
        this.movies = this.mov.results;
        this.currentPage = this.mov.page;
        this.nextPage = this.currentPage + 1;
        this.totalPage = this.mov.total_pages;
      },
      error: (error) => {
        alert('Failed to Fetch Movies Due to Server Error!');
      },
    });
    this.getAllGenres();
  }

  getAllGenres() {
    this.movieService.getGenres().subscribe({
      next: (data) => {
        this.genres = data;
      },
      error: (error) => {
        alert('Failed to Fetch Movies Due to Server Error!');
      },
    });
  }

  searchMovie($event: string) {
    this.movieSearch = $event;
    if (this.movieSearch.length) {
      this.movieService.getSearchedMovies(this.movieSearch).subscribe({
        next: (data) => {
          this.mov = data;
          this.searchedMovies = this.mov.results;
        },
      });
    } else {
      this.getAllMovies();
    }
  }

  filterByLang($event: string) {
    this.langByChip = $event;
    if (this.langByChip.length) {
      if (this.genreSelection.length) {
        this.movieService
          .getMoviesByGenreAndLang(this.langByChip, this.genreSelection)
          .subscribe({
            next: (data) => {
              this.movies = data.results;
              this.currentPage = data.page;
              this.nextPage = this.currentPage + 1;
              this.totalPage = data.total_pages;
            },
          });
      } else {
        this.movieService.getMoviesByLang(this.langByChip).subscribe({
          next: (data) => {
            this.movies = data.results;
            this.currentPage = data.page;
            this.nextPage = this.currentPage + 1;
            this.totalPage = data.total_pages;
          },
        });
      }
    } else {
      this.getAllMovies();
    }
  }

  @HostListener('window:scroll', ['$event'])
  onScroll(event: Event): void {
    const scrollPosition = window.scrollY + window.innerHeight;
    const documentHeight = document.documentElement.scrollHeight;
    if (scrollPosition >= documentHeight - 200) {
      this.next();
    }
    if (window.scrollY > 0) {
      this.isScrolledDown = true;
    } else {
      this.isScrolledDown = false;
    }
  }

  next() {
    if (this.nextPage <= this.totalPage) {
      if (this.langByChip.length) {
        if (this.genreSelection.length) {
          this.movieService
            .getMoviesByGenreAndLangWithPage(
              this.langByChip,
              this.genreSelection,
              this.nextPage
            )
            .subscribe({
              next: (data) => {
                this.currentPage = data.page;

                this.newMovies = data.results.filter(
                  (newMovie: any) =>
                    !this.movies.some(
                      (existingMovie) => existingMovie.id === newMovie.id
                    )
                );
                this.movies = [...this.movies, ...this.newMovies];
                this.nextPage = this.currentPage + 1;
                this.totalPage = data.total_pages;
              },
            });
        } else {
          this.loadMoviesByLangAndPage();
        }
      } else if (this.genreSelection.length) {
        this.movieService
          .getMoviesByGenreWithPage(this.genreSelection, this.nextPage)
          .subscribe({
            next: (data) => {
              this.currentPage = data.page;
              this.newMovies = data.results.filter(
                (newMovie: any) =>
                  !this.movies.some(
                    (existingMovie) => existingMovie.id === newMovie.id
                  )
              );
              this.movies = [...this.movies, ...this.newMovies];
              this.nextPage = this.currentPage + 1;
              this.totalPage = data.total_pages;
            },
          });
      } else {
        this.movieService.getMoviesBypage(this.nextPage).subscribe({
          next: (data) => {
            this.currentPage = data.page;
            this.newMovies = data.results.filter(
              (newMovie: any) =>
                !this.movies.some(
                  (existingMovie) => existingMovie.id === newMovie.id
                )
            );
            this.movies = [...this.movies, ...this.newMovies];
            this.nextPage = this.currentPage + 1;
            this.totalPage = data.total_pages;
          },
        });
      }
    }
  }

  loadMoviesByLangAndPage() {
    this.movieService
      .getMoviesByLangAndPage(this.langByChip, this.nextPage)
      .subscribe({
        next: (data) => {
          this.newMovies = data.results.filter(
            (newMovie: any) =>
              !this.movies.some(
                (existingMovie) => existingMovie.id === newMovie.id
              )
          );
          this.movies = [...this.movies, ...this.newMovies];
          this.currentPage = data.page;
          this.nextPage = this.currentPage + 1;
        },
      });
  }

  getTrendingMovies() {
    this.movieService.getTrendingMovies().subscribe({
      next: (data) => {
        this.trendingMovies = data.results;
      },
    });
  }

  getNow_playingMovies() {
    this.movieService.getNow_playingMovies().subscribe({
      next: (data) => {
        this.now_playingMovies = data.results;
      },
    });
  }
}
