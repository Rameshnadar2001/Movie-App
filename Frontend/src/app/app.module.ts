import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppComponent } from './app.component';

import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { LandingViewComponent } from './landing-view/landing-view.component';
import { MovieCardComponent } from './movie-card/movie-card.component';
import {MatCardModule} from '@angular/material/card';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MovieDetailComponent } from './movie-detail/movie-detail.component';
import { MovieRecommendationComponent } from './movie-recommendation/movie-recommendation.component';
import { AppRoutingModule } from './app-routing/app-routing.module';
import { SearchComponent } from './search/search.component';
import { MatFormFieldModule } from '@angular/material/form-field';
import {MatChipsModule} from '@angular/material/chips';
import { MatInputModule } from '@angular/material/input';
import { FilterComponent } from './filter/filter.component';
import { LoginComponent } from './login/login.component';
import { ResgistrationFormComponent } from './resgistration-form/resgistration-form.component';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatButtonModule } from '@angular/material/button';
import { HeaderComponent } from './header/header.component';
import { FavoriteMoviesComponent } from './favorite-movies/favorite-movies.component';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { MatIconModule } from '@angular/material/icon'; 
import { MatTooltipModule } from '@angular/material/tooltip';
import { FooterComponent } from './footer/footer.component';
import { ForgotPasswordComponent } from './forgot-password/forgot-password.component';
import { NotFoundComponent } from './not-found/not-found.component';
@NgModule({
  declarations: [
    AppComponent,
    LandingViewComponent,
    MovieCardComponent,
    MovieDetailComponent,
    MovieRecommendationComponent,
    SearchComponent,
    FilterComponent,
    LoginComponent,
    ResgistrationFormComponent,
    HeaderComponent,
    FavoriteMoviesComponent,
    FooterComponent,
    ForgotPasswordComponent,
    NotFoundComponent,
  ],
  imports: [
    BrowserModule,
    FormsModule,
    HttpClientModule,
    MatCardModule,
    BrowserAnimationsModule,
    AppRoutingModule,
    MatFormFieldModule,
    MatChipsModule,
    MatToolbarModule,
    MatButtonModule,
    MatSnackBarModule,
    MatInputModule,
    MatIconModule,
    MatTooltipModule,
    ReactiveFormsModule,
  ],
  providers: [],
  bootstrap: [AppComponent],
})
export class AppModule {}
