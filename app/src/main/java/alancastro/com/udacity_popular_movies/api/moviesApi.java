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
    @GET("/discover/movie?sort=popularity.desc")      //here is the other url part.best way is to start using /
    public void getFeed(@Query("api_key") String api_key, Callback<List<movieModel>> response);
    //response is the response from the server which is now in the POJO
}
