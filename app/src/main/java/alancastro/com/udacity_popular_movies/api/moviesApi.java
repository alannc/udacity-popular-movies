package alancastro.com.udacity_popular_movies.api;

import java.util.List;

import alancastro.com.udacity_popular_movies.model.movieModel;
import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by alannnc on 4/10/15.
 */
public interface moviesApi {
    @GET("/discover/movie?sort_by=popularity.desc")
    void getPopular(@Query("api_key") String api_key, Callback<List<movieModel>> response);

    @GET("/discover/movie?sort_by=vote_average.desc")
    void getHighestRated(@Query("api_key") String api_key, Callback<List<movieModel>> response);

}
