package com.jap.UserMovieService.Service;

import com.jap.UserMovieService.Domain.Genre;
import com.jap.UserMovieService.Domain.MovieRecommendation;

import java.util.List;
import java.util.Map;

public interface UserMovieService {
    Map<String,Object>getALlMovies();
    //or
    Map<String,Object>getAllMoviesByLanguageAndPageNo(String lang,int pageNo);
    List<MovieRecommendation>getMovieRecommendationById(int id);
    Map<String, Object>getMovieById(int id);
    List<Genre>getAllGenres();
    Map<String,Object>getMoviesByLang(String lang);
    Map<String,Object>getSearchedMovies(String name);
    Map<String,Object>getMoviesByPage(long page);
    Map<String,Object>getMoviesByGenre(long genreId);
    Map<String,Object>getMoviesByLangAndGenre(String lang,long genreId);
    Map<String,Object>getMoviesByPageInLangAndGenre(String lang,long genreId,long Page);
    Map<String,Object>getMoviesByPageAndGenre(long genreId,long page);

    List<Map<String,Object>>getMovieVideos(long movieId) throws Exception;
    Map<String,Object>getNowPlayingMovies();
    Map<String,Object>getTrendingMovies();
}
