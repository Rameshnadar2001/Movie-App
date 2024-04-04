package com.movie.favourite.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Set;

@Document
public class User {

    @Id
    private String emailId;
    private  String password;
    private  String userName;
    private  int   age;
    private  String profileImage;
    private List<Movie> favoriteMovieList;

    public User() {
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public List<Movie> getFavoriteMovieList() {
        return favoriteMovieList;
    }

    public void setFavoriteMovieList(List<Movie> favoriteMovieList) {
        this.favoriteMovieList = favoriteMovieList;
    }

    @Override
    public String toString() {
        return "User{" +
                "emailId='" + emailId + '\'' +
                ", password='" + password + '\'' +
                ", userName='" + userName + '\'' +
                ", age=" + age +
                ", profileImage=" + profileImage +
                ", favoriteMovieList=" + favoriteMovieList +
                '}';
    }
}
