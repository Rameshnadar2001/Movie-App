import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { RouterServiceService } from '../services/router-service.service';

@Injectable({
  providedIn: 'root'
})
export class AuthServiceService {

  constructor(private http: HttpClient, private router: RouterServiceService) { }
  isLoggedIn = false;
  token:any;
  userData?: any;
  login(token:any){
    this.token = token;
    this.http.get('http://localhost:9000/api/v2/user/getUserDetails',{
      observe: 'response',
      headers: { Authorization: 'Bearer ' + token },
    }).subscribe({
      next:data=>{
        this.userData = data.body;
        this.isLoggedIn = true;
        this.router.navigateToHome();
      },
      error: error=>{
        alert(error.error);
      }
    })
  }
}
