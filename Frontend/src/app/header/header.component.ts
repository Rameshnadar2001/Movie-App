import { Component, EventEmitter, Input, Output } from '@angular/core';
import { UserServiceService } from '../services/user-service.service';
import { AuthServiceService } from '../services/auth-service.service';
import { RouterServiceService } from '../services/router-service.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css'],
})
export class HeaderComponent {
  constructor(
    private authService: AuthServiceService,
    private userService: UserServiceService,
    private router: RouterServiceService
  ) {}
  
  profileImage: any;
  isLoggedIn?: boolean;
  userData?: any;
  showDropdown: boolean = false;
  @Input() isPresentInLogin: boolean = false;
  @Input() isPresentInSignup: boolean = false;
  @Input() isFav: any;
  @Input() isNotFound: any;
  searchText: any;
  @Output() onMovieSearch: EventEmitter<string> = new EventEmitter<string>();

  searchExport($event: any) {
    this.searchText = $event;
    console.log(this.searchText, 'Header search');
    this.onMovieSearch.emit(this.searchText);
  }

  ngOnInit(): void {
    this.userData = this.authService.userData;
    this.isLoggedIn = this.authService.isLoggedIn;
    if (this.authService.isLoggedIn) {
      this.profileImage = this.userData.profileImage;
    }
  }

  toggleDropdown() {
    this.showDropdown = !this.showDropdown;
  }

  logout() {
    this.authService.isLoggedIn = false;
    this.router.navigateToLandingView();
  }
}
