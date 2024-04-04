package com.jap.UserMovieService.Controller;

import com.jap.UserMovieService.Domain.Genre;
import com.jap.UserMovieService.Domain.MovieRecommendation;
import com.jap.UserMovieService.Service.UserMovieService;
import com.jap.UserMovieService.Service.UserMovieServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class UserMovieServiceController {
    private UserMovieServiceImpl userMovieService;
    private ResponseEntity<?> responseEntity;
    @Autowired
    public UserMovieServiceController(UserMovieServiceImpl userMovieService){
        this.userMovieService=userMovieService;
    }
    @GetMapping("/movies")
    public ResponseEntity<?>getAllMovies(){
        return new ResponseEntity<>(userMovieService.getALlMovies(), HttpStatus.OK);
    }
    //for landing view
    @GetMapping("/movies/{lang}/{pageNo}")
    public ResponseEntity<?>getAllMoviesByLangAndPageNo(@PathVariable String lang,@PathVariable int pageNo){
        Map<String,Object>movies=userMovieService.getAllMoviesByLanguageAndPageNo(lang,pageNo);
        return new ResponseEntity<>(movies, HttpStatus.OK);
    }
    //for recommendation section
    @GetMapping("/movie/recommendation/{movieId}")
    public ResponseEntity<?>getAllMoviesByRecommendationId(@PathVariable int movieId){
        List<MovieRecommendation>movies=userMovieService.getMovieRecommendationById(movieId);
        return new ResponseEntity<>(movies, HttpStatus.OK);
    }
    @GetMapping("/genres")
    public ResponseEntity<?>getAllMoviesGenres(){
        List<Genre>genreList=userMovieService.getAllGenres();
        return new ResponseEntity<>(genreList, HttpStatus.OK);
    }
    @GetMapping("/movies-detail/{movieId}")
    public ResponseEntity<?>getAllMoviesById(@PathVariable int movieId){
        Map<String, Object>response=userMovieService.getMovieById(movieId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @GetMapping("/movies-By-lang/{lang}")
    public ResponseEntity<?>getAllMoviesByLang(@PathVariable String lang){
        Map<String, Object>response=userMovieService.getMoviesByLang(lang);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @GetMapping("/movies-By-page/{page}")
    public ResponseEntity<?>getAllMoviesByPage(@PathVariable long page){
        Map<String, Object>response=userMovieService.getMoviesByPage(page);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @GetMapping("/movies-By-movieName/{movieName}")
    public ResponseEntity<?>movieSearchByMovieName(@PathVariable String movieName){
        Map<String, Object>response=userMovieService.getSearchedMovies(movieName);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @GetMapping("/movies-By-Genre/{genre}")
    public ResponseEntity<?>getMoviesByGenre(@PathVariable long genre){
        Map<String, Object>response=userMovieService.getMoviesByGenre(genre);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @GetMapping("/movies-By-GenreAndLang/{lang}/{genreId}")
    public ResponseEntity<?>getMoviesByLangAndGenre(@PathVariable String lang,@PathVariable long genreId){
        Map<String, Object>response=userMovieService.getMoviesByLangAndGenre(lang,genreId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @GetMapping("/movies-By-GenreAndLang-Page/{lang}/{genreId}/{page}")
    public ResponseEntity<?>getMoviesByLangAndGenreAndPage(@PathVariable String lang,@PathVariable long genreId,@PathVariable long page){
        Map<String, Object>response=userMovieService.getMoviesByPageInLangAndGenre(lang,genreId,page);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/movies-By-Genre-Page/{genreId}/{page}")
    public ResponseEntity<?>getMoviesByGenreAndPage(@PathVariable long genreId,@PathVariable long page){
        Map<String, Object>response=userMovieService.getMoviesByPageAndGenre(genreId,page);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @GetMapping("/movie/{movieId}/videos")
    public ResponseEntity<?>getMovieVideosById(@PathVariable long movieId)
    {
        try{
            responseEntity=new ResponseEntity<>(userMovieService.getMovieVideos(movieId),HttpStatus.OK) ;
        } catch (Exception e) {
            return new ResponseEntity<>("Video Not Found",HttpStatus.NOT_FOUND);
        }
        return responseEntity;
    }
    @GetMapping("/trending/movies/day")
    public ResponseEntity<?>getTrendingMovies()
    {
        responseEntity=new ResponseEntity<>(userMovieService.getTrendingMovies(),HttpStatus.OK) ;
        return responseEntity;
    }
    @GetMapping("/now_playing/movies")
    public ResponseEntity<?>getNowPlaying()
    {
        return  new ResponseEntity<>(userMovieService.getNowPlayingMovies(),HttpStatus.OK) ;
    }



}
