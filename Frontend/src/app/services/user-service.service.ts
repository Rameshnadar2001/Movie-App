import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { User } from '../models/User';
import { Observable } from 'rxjs';
import { AuthServiceService } from './auth-service.service';
import { MovieRecommendationService } from './movie-recommendation.service';

@Injectable({
  providedIn: 'root',
})
export class UserServiceService {
  constructor(
    private http: HttpClient,
    private authService: AuthServiceService,
    private movieService: MovieRecommendationService
  ) {}
  videoKey: any;
  favoriteMovies: any[] = [];
  apiUrl: string = 'http://localhost:9000/api/v2/register';
  
  registerUser(user: FormData): Observable<any> {
    return this.http.post<any>(this.apiUrl, user);
  }

  updateUser(user: any): Observable<any> {
    return this.http.put<any>(
      `http://localhost:9000/api/v2/updatePassword`,
      user
    );
  }

  generateToken(user: User) {
    return this.http.post('http://localhost:9000/api/v0/login', user, {
      responseType: 'text' as 'json',
    });
  }

  addToFavoriteMovies(movie: any): Observable<any> {
    return this.http.post('http://localhost:9000/api/v2/user/movie', movie, {
      observe: 'response',
      headers: { Authorization: 'Bearer ' + this.authService.token },
    });
  }

  getFavoriteMovies(token: any): Observable<any> {
    return this.http.get('http://localhost:9000/api/v2/user/movies', {
      observe: 'response',
      headers: { Authorization: 'Bearer ' + token },
    });
  }

  deleteMovieFromFavorite(id: number): Observable<any> {
    return this.http.delete(`http://localhost:9000/api/v2/user/movie/${id}`, {
      observe: 'response',
      headers: { Authorization: 'Bearer ' + this.authService.token },
    });
  }

  getVideo(id: any): Promise<any> {
    return new Promise((resolve, reject) => {
      this.movieService.getMovieViedosById(id).subscribe({
        next: (data) => {
          let trailers: any[] = [];
          let teaser: any[] = [];
          let clip: any[] = [];
          let featureClips: any[] = [];
          let behindTheScenes: any[] = [];

          data.forEach((element: any) => {
            if (element.type == 'Trailer') {
              trailers.push(element);
            }
            if (element.type == 'Teaser') {
              teaser.push(element);
            }
            if (element.type == 'Clip') {
              clip.push(element);
            }
            if (element.type == 'Behind the Scenes') {
              behindTheScenes.push(element);
            }
            if (element.type == 'Featurette') {
              featureClips.push(element);
            }
          });

          if (trailers.length) {
            this.videoKey = trailers[0].key;
          } else if (teaser.length) {
            this.videoKey = teaser[0].key;
          } else if (clip.length) {
            this.videoKey = clip[0].key;
          } else if (behindTheScenes.length) {
            this.videoKey = behindTheScenes[0].key;
          } else if (featureClips.length) {
            this.videoKey = featureClips[0].key;
          }
          resolve(this.videoKey);
        },
        error: (error) => {
          reject(error);
        },
      });
    });
  }
}
