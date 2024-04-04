import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LandingViewComponent } from '../landing-view/landing-view.component';
import { MovieDetailComponent } from '../movie-detail/movie-detail.component';
import { LoginComponent } from '../login/login.component';
import { ResgistrationFormComponent } from '../resgistration-form/resgistration-form.component';
import { FavoriteMoviesComponent } from '../favorite-movies/favorite-movies.component';
import { ForgotPasswordComponent } from '../forgot-password/forgot-password.component';
import { CanDeactivateGuard } from '../can-deactivate.guard';
import { NotFoundComponent } from '../not-found/not-found.component';
const routes: Routes = [
  {
    path: 'movie-detail/:id',
    component: MovieDetailComponent,
  },
  {
    path: 'login',
    component: LoginComponent,
  },
  {
    path: 'user/home',
    component: LandingViewComponent,
  },
  {
    path: 'home',
    component: LandingViewComponent,
  },
  {
    path: '',
    redirectTo: 'home',
    pathMatch: 'full',
  },
  {
    path: 'register',
    component: ResgistrationFormComponent,
    canDeactivate: [CanDeactivateGuard],
  },
  {
    path: 'favoriteMovies',
    component: FavoriteMoviesComponent,
  },
  {
    path: 'forgotPassword',
    component: ForgotPasswordComponent,
  },
  {
    path: '**',
    component: NotFoundComponent,
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
