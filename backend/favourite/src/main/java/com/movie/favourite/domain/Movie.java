package com.movie.favourite.domain;

import org.springframework.data.annotation.Id;

public class Movie {
@Id
   private Long id;
   private String overview;
   private String poster_path;
   private String title;
   private float vote_average;
   private String original_language;

    public Movie() {
    }

    public String getOriginal_language() {
        return original_language;
    }

    public void setOriginal_language(String original_language) {
        this.original_language = original_language;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public float getVote_average() {
        return vote_average;
    }

    public void setVote_average(float vote_average) {
        this.vote_average = vote_average;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "id=" + id +
                ", overview='" + overview + '\'' +
                ", poster_path='" + poster_path + '\'' +
                ", title='" + title + '\'' +
                ", vote_average=" + vote_average +
                ", original_language='" + original_language + '\'' +
                '}';
    }
}
