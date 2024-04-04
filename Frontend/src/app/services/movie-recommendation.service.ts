import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class MovieRecommendationService {
  constructor(private http: HttpClient) {}

  urlForAllMovies: string = 'http://localhost:9000/api/v1/movies';
  urlForALLGenres: string = 'http://localhost:9000/api/v1/genres';
  urlForGettingMoviesById = 'http://localhost:9000/api/v1/movies-detail';
  urlForMovieRecommendation =
    'http://localhost:9000/api/v1/movie/recommendation';

  //
  urlForGettingMoviesByPageNo = 'http://localhost:9000/api/v1/movies-By-page/';

  //
  urlForGettingMoviesByLang = 'http://localhost:9000/api/v1/movies-By-lang/';

  urlForGettingMoviesByLangAndPage = 'http://localhost:9000/api/v1/movies';

  urlForSearchedMovie = 'http://localhost:9000/api/v1/movies-By-movieName/';

  urlForGettingMoviesByGenre = 'http://localhost:9000/api/v1/movies-By-Genre/';
  urlForGettingMoviesByLangAndGenre =
    'http://localhost:9000/api/v1/movies-By-GenreAndLang';

  urlForGettingMoviesByGenreWithPage =
    'http://localhost:9000/api/v1/movies-By-Genre-Page';
  urlForGettingMoviesByGenreAndLangWithPage =
    'http://localhost:9000/api/v1/movies-By-GenreAndLang-Page';

  urlForGettingTrendingMovies =
    'http://localhost:9000/api/v1/trending/movies/day';
  urlForGettingNow_playingMovies =
    'http://localhost:9000/api/v1/now_playing/movies';

  getMoviesByGenre(genreId: string): Observable<any> {
    return this.http.get<any>(`${this.urlForGettingMoviesByGenre}${genreId}`);
  }

  getMoviesByGenreAndLang(lang: string, genreId: string) {
    return this.http.get<any>(
      `${this.urlForGettingMoviesByLangAndGenre}/${lang}/${genreId}`
    );
  }
  getMoviesByGenreAndLangWithPage(lang: string, genreId: string, page: number) {
    return this.http.get<any>(
      `${this.urlForGettingMoviesByGenreAndLangWithPage}/${lang}/${genreId}/${page}`
    );
  }
  getMoviesByGenreWithPage(genreId: string, page: number) {
    return this.http.get<any>(
      `${this.urlForGettingMoviesByGenreWithPage}/${genreId}/${page}`
    );
  }

  getMovies(): Observable<any> {
    return this.http.get<any>(this.urlForAllMovies);
  }
  getGenres(): Observable<any[]> {
    return this.http.get<any[]>(this.urlForALLGenres);
  }
  getMoviesById(id: string): Observable<any> {
    return this.http.get<any>(`${this.urlForGettingMoviesById}/${id}`);
  }
  getMoviesRecommendation(id: string): Observable<any[]> {
    return this.http.get<any[]>(`${this.urlForMovieRecommendation}/${id}`);
  }
  //** */
  getSearchedMovies(name: string): Observable<any> {
    return this.http.get<any>(`${this.urlForSearchedMovie}${name}`);
  }

  getMoviesBypage(page: number): Observable<any> {
    return this.http.get<any>(`${this.urlForGettingMoviesByPageNo}${page}`);
  }
  getMoviesByLang(lang: string): Observable<any> {
    return this.http.get<any>(`${this.urlForGettingMoviesByLang}${lang}`);
  }
  getMoviesByLangAndPage(language: string, page: number): Observable<any> {
    return this.http.get<any>(
      `${this.urlForGettingMoviesByLangAndPage}/${language}/${page}`
    );
  }

  getMovieViedosById(id: string): Observable<any> {
    return this.http.get<any>(
      `http://localhost:9000/api/v1/movie/${id}/videos`
    );
  }
  getTrendingMovies() {
    return this.http.get<any>(`${this.urlForGettingTrendingMovies}`);
  }
  getNow_playingMovies() {
    return this.http.get<any>(`${this.urlForGettingNow_playingMovies}`);
  }

  openVideo(videoKey: any) {
    if (videoKey) {
      const videoUrl = `https://www.youtube.com/watch?v=${videoKey}`;
      window.open(videoUrl, '_blank');
    } else {
      alert('Video Not Found');
    }
  }
}
