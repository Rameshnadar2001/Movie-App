package com.jap.UserMovieService.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jap.UserMovieService.Domain.Genre;
import com.jap.UserMovieService.Domain.MovieRecommendation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserMovieServiceImpl implements UserMovieService{
private RestTemplate restTemplate;
private ObjectMapper objectMapper;
    @Autowired
    public UserMovieServiceImpl(RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }
 private String baseUrl="https://api.themoviedb.org/3";

 private String discoverUrl= "/discover/movie";
 @Value("${tmdb.api.key}")
 private String API_KEY;
    @Override
    public Map<String,Object> getALlMovies() {
        StringBuilder urlBuilder=new StringBuilder(baseUrl);
        String url=urlBuilder.append(discoverUrl).append("?api_key=").append(API_KEY).toString();
        System.out.println("Url in GetALLMovies: "+url);
       Map<String,Object> response=restTemplate.getForEntity(url,Map.class).getBody();
        if(response!=null){
            return response;
        }else {
            return  null;
        }
    }

    @Override
    public Map<String,Object> getAllMoviesByLanguageAndPageNo(String lang, int pageNo) {
        StringBuilder urlBuilder=new StringBuilder(baseUrl);
        String url=urlBuilder.append(discoverUrl).append("?api_key=").append(API_KEY)
                .append("&with_original_language=").append(lang).append("&page=")
                .append(pageNo).toString();
        System.out.println("Url in getAllMoviesByLanguageAndPageNo: "+url);
        Map<String,Object> response=restTemplate.getForEntity(url,Map.class).getBody();
        if(response!=null){
            return response;
        }else {
            return  null;
        }
    }

    @Override
    public List<MovieRecommendation> getMovieRecommendationById(int id) {
        //passes the response of any one url which is not null out of multiple url
        //urls used:by recommend,by similar,by any simple url search
        //1)"https://api.themoviedb.org/3/movie/{movie_id}/recommendations"

        StringBuilder urlBuilder = new StringBuilder(baseUrl);
        String url = urlBuilder.append("/movie/").append(id).append("/recommendations")
                .append("?api_key=").append(API_KEY).toString();
        System.out.println("Url in getMovieRecommendationById: " + url);
        //getting request for first url
        Map<String, Object> response = restTemplate.getForEntity(url, Map.class).getBody();

        if (response != null && response.containsKey("results")) {
            List<Map<String, Object>> results = (List<Map<String, Object>>) response.get("results");
            int size = results.size();
            System.out.println(size);
            //if the results array is empty from recommendation then call the details api of
            //that movie id so we can get the genres and lang of that movie
            if (size == 0) {

                StringBuilder url2Builder = new StringBuilder(baseUrl);
                //Building Url for Movie Details to get the details of the given movieId
                String url2 = url2Builder.append("/movie/").append(id)
                        .append("?api_key=").append(API_KEY).toString();
                System.out.println("Url-2 in getMovieRecommendationById: " + url2);
                Map<String, Object> response2 = restTemplate.getForEntity(url2, Map.class).getBody();
                 //checking whether genres key exists or not
                if (response2 != null && response2.containsKey("genres")) {
                    // getting genreList from genres key in Map type
                    List<Map<String, Object>> genreList = (List<Map<String, Object>>) response2.get("genres");
                    //getting the language of that movie
                    String lang = response2.get("original_language").toString();

                    if (!genreList.isEmpty()) {
                        List<Genre> genres = genreList.stream().map(genre -> objectMapper.convertValue(genre, Genre.class))
                                .collect(Collectors.toList());
                        StringBuilder joinedIds = new StringBuilder();
                       //concatenate genreId from genres in String
                        for (Genre genre : genres) {
                            joinedIds.append(genre.getId()).append(",");
                        }
                        // converting to string
                        String idsGenre = joinedIds.toString();
                        StringBuilder url3Builder = new StringBuilder(baseUrl);
                        //Building Url to get Movies filtered by Genres and Language
                        String url3 = url3Builder.append(discoverUrl).append("?api_key=").append(API_KEY)
                                .append("&with_original_language=").append(lang).append("&with_genres=")
                                .append(idsGenre).toString();
                        System.out.println("URL 3 " + url3);

                        //making a HttpGetRequest to get Movies filtered by Genres and Language in
                        // return getting responseEntity Body
                        Map<String, Object> response3 = restTemplate.getForEntity(url3, Map.class).getBody();

                        if (response3 != null && response3.containsKey("results")) {
                            List<Map<String, Object>> results3 = (List<Map<String, Object>>) response3.get("results");
                           return results3.stream()
                                    .map(result -> objectMapper
                                            .convertValue(result, MovieRecommendation.class))
                                    .collect(Collectors.toList());
                        }
                    } else {
                        //when genres list is empty then we get the movie by language
                            StringBuilder url4Builder = new StringBuilder(baseUrl);
                            String url4 = url4Builder.append(discoverUrl).append("?api_key=").append(API_KEY)
                                .append("&with_original_language=").append(lang).toString();
                            Map<String, Object> response4 = restTemplate.getForEntity(url4, Map.class).getBody();
                            if (response4 != null && response4.containsKey("results")) {
                            List<Map<String, Object>> results4 = (List<Map<String, Object>>) response4.get("results");
                            return results4.stream()
                                    .map(result -> objectMapper
                                            .convertValue(result, MovieRecommendation.class))
                                    .collect(Collectors.toList());

                        }


                    }

                }
            }
           return results.stream().map(result -> objectMapper.convertValue(result, MovieRecommendation.class))
                    .collect(Collectors.toList());
        }else{
            return Collections.emptyList();
        }

    }

    @Override
    public Map<String,Object> getMovieById(int id) {
        StringBuilder url2Builder = new StringBuilder(baseUrl);
        //Building Url for Movie Details to get the details of the given movieId
        String url = url2Builder.append("/movie/").append(id)
                .append("?api_key=").append(API_KEY).toString();
        System.out.println("Url-2 in getMovieRecommendationById: " + url);
        Map<String, Object> response = restTemplate.getForEntity(url, Map.class).getBody();
        return response;
    }

    @Override
    public List<Genre> getAllGenres() {

        StringBuilder urlBuilder = new StringBuilder(baseUrl);
        String url = urlBuilder.append("/genre/movie/list").append("?api_key=").append(API_KEY).toString();
       Map<String,Object>response=restTemplate.getForEntity(url, Map.class).getBody();
        if (response != null && response.containsKey("genres")) {
            List<Map<String, Object>> results = (List<Map<String, Object>>) response.get("genres");
            return results.stream()
                    .map(result -> objectMapper
                            .convertValue(result, Genre.class))
                    .collect(Collectors.toList());
        }
        else{
            return Collections.emptyList();
        }
    }

    @Override
    public Map<String, Object> getMoviesByLang(String lang) {

        StringBuilder urlBuilder = new StringBuilder(baseUrl);
        String url=urlBuilder.append(discoverUrl).append("?api_key=").append(API_KEY)
                .append("&with_original_language=").append(lang).toString();
        Map<String,Object>response=restTemplate.getForEntity(url, Map.class).getBody();
        if(response!=null){
            return response;
        }else {
            return  null;
        }
    }

    @Override
    public Map<String, Object> getSearchedMovies(String name) {
        StringBuilder urlBuilder = new StringBuilder(baseUrl);
        String url=urlBuilder.append("/search/movie").append("?api_key=").append(API_KEY)
                .append("&query=").append(name).toString();
        Map<String,Object>response=restTemplate.getForEntity(url, Map.class).getBody();
        if(response!=null){
            return response;
        }else {
            return  null;
        }
    }

    @Override
    public Map<String, Object> getMoviesByPage(long page) {
        StringBuilder urlBuilder = new StringBuilder(baseUrl);
        String url=urlBuilder.append(discoverUrl).append("?api_key=").append(API_KEY)
                .append("&page=").append(page).toString();
        Map<String,Object>response=restTemplate.getForEntity(url, Map.class).getBody();
        if(response!=null){
            return response;
        }else {
            return  null;
        }
    }

    @Override
    public Map<String, Object> getMoviesByGenre(long genreId) {
        StringBuilder urlBuilder = new StringBuilder(baseUrl);
        //Building Url to get Movies filtered by Genres and Language
        String url = urlBuilder
                .append(discoverUrl)
                .append("?api_key=")
                .append(API_KEY)
                .append("&with_genres=")
                .append(genreId).toString();
        System.out.println("URL 3 " + url);
        Map<String, Object> response = restTemplate.getForEntity(url, Map.class).getBody();

        if(response!=null){
            return response;
        }else {
            return  null;
        }
    }

    @Override
    public Map<String, Object> getMoviesByLangAndGenre(String lang,long genreId) {
        StringBuilder urlBuilder = new StringBuilder(baseUrl);
        //Building Url to get Movies filtered by Genres and Language
        String url = urlBuilder.append(discoverUrl).append("?api_key=").append(API_KEY)
                .append("&with_original_language=").append(lang).append("&with_genres=")
                .append(genreId).toString();
        Map<String, Object> response = restTemplate.getForEntity(url, Map.class).getBody();

        if(response!=null){
            return response;
        }else {
            return  null;
        }

    }

    @Override
    public Map<String, Object> getMoviesByPageInLangAndGenre(String lang, long genreId, long page) {

        StringBuilder urlBuilder = new StringBuilder(baseUrl);
        //Building Url to get Movies filtered by Genres and Language
        String url = urlBuilder.append(discoverUrl).append("?api_key=").append(API_KEY)
                .append("&with_original_language=").append(lang).append("&with_genres=")
                .append(genreId).append("&page=").append(page).toString();

        Map<String, Object> response = restTemplate.getForEntity(url, Map.class).getBody();

        if(response!=null){
            return response;
        }else {
            return  null;
        }
    }

    @Override
    public Map<String, Object> getMoviesByPageAndGenre(long genreId, long page) {
        StringBuilder urlBuilder = new StringBuilder(baseUrl);
        //Building Url to get Movies filtered by Genres and Language
        String url = urlBuilder.append(discoverUrl)
                .append("?api_key=")
                .append(API_KEY)
                .append("&with_genres=")
                .append(genreId).append("&page=").append(page).toString();
        Map<String, Object> response = restTemplate.getForEntity(url, Map.class).getBody();

        if(response!=null){
            return response;
        }else {
            return  null;
        }
    }

    @Override
    public List<Map<String,Object>> getMovieVideos(long movieId) throws Exception {
        StringBuilder urlBuilder = new StringBuilder(baseUrl);
        //Building Url to get Movies filtered by Genres and Language
        //https://api.themoviedb.org/3/movie/23424/videos?api_key=2590672a85d3b7b98a318254c15369a0

        String url = urlBuilder.append("/movie/").append(movieId).append("/videos")
                .append("?api_key=")
                .append(API_KEY)
                .toString();
        Map<String, Object> response = restTemplate.getForEntity(url, Map.class).getBody();
        if(response!=null&&response.containsKey("results")){
            List<Map<String,Object>> result= (List<Map<String,Object>>)response.get("results");

            if(result.isEmpty()){
                throw new Exception();
            }else {
                return result;
            }

        }else{
            throw new Exception();
        }
    }

    @Override
    public Map<String,Object> getNowPlayingMovies() {
        //https://api.themoviedb.org/3/movie/now_playing?api_key=2590672a85d3b7b98a318254c15369a0
        StringBuilder urlBuilder = new StringBuilder(baseUrl);
        String url = urlBuilder.append("/movie/").append("now_playing")
                .append("?api_key=")
                .append(API_KEY)
                .toString();
        Map<String, Object> response = restTemplate.getForEntity(url, Map.class).getBody();
        if(response!=null){
            return response;
        }else {
            return  null;
        }
    }

    @Override
    public Map<String, Object> getTrendingMovies() {
        //https://api.themoviedb.org/3/trending/movie/day?api_key=2590672a85d3b7b98a318254c15369a0
        StringBuilder urlBuilder = new StringBuilder(baseUrl);
        String url = urlBuilder.append("/trending/").append("movie/day")
                .append("?api_key=")
                .append(API_KEY)
                .toString();
        Map<String, Object> response = restTemplate.getForEntity(url, Map.class).getBody();
        if(response!=null){
            return response;
        }else {
            return  null;
        }
    }
}