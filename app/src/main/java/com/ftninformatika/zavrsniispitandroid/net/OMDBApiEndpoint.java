package com.ftninformatika.zavrsniispitandroid.net;

import com.ftninformatika.zavrsniispitandroid.model.Movie;
import com.ftninformatika.zavrsniispitandroid.model.SearchResult;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

public interface OMDBApiEndpoint {

    //http://www.omdbapi.com/?apikey=[yourkey]&s=Batman
    @GET("/")
    Call<SearchResult> searchOMDB(@QueryMap Map<String, String> options);

    //ovaj get se mora napraviti da bi se mogao uraditi novi search koji vraca Movie kao parametar
    @GET("/")
    Call<Movie> searchMovieOMDB(@QueryMap Map<String, String> options);
}
