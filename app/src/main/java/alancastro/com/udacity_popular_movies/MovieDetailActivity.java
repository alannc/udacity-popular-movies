package alancastro.com.udacity_popular_movies;

import android.app.Activity;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import alancastro.com.udacity_popular_movies.adapter.RVMovieReviewAdapter;
import alancastro.com.udacity_popular_movies.adapter.RVMovieTrailerAdapter;
import alancastro.com.udacity_popular_movies.model.movieReviewModel;
import alancastro.com.udacity_popular_movies.model.movieTrailerModel;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.converter.GsonConverter;

/**
 * Created by alannnc on 9/10/15.
 */
public class MovieDetailActivity extends Activity {

    private static final String API_URL = "http://api.themoviedb.org/3";
    private static final String API_KEY = "6c586439d08f9cfbc64f4e5a92aa1408";

    private alancastro.com.udacity_popular_movies.api.moviesApi moviesApi;

    private String idMovie;
    private String movieTitle;
    private String releaseDate;
    private String posterPath;
    private String voteAverage;
    private String overview;

    private RecyclerView rvTrailer;
    private RecyclerView rvReview;
    private GridLayoutManager gridLayoutManager;

    @Override
    public void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_movie_detail);

        rvTrailer = (RecyclerView)findViewById(R.id.rvTrailers);
        gridLayoutManager = new GridLayoutManager(this, 1);
        rvTrailer.setLayoutManager(gridLayoutManager);
        rvTrailer.setHasFixedSize(true);

        rvReview = (RecyclerView)findViewById(R.id.rvReviews);
        gridLayoutManager = new GridLayoutManager(this, 1);
        rvReview.setLayoutManager(gridLayoutManager);
        rvReview.setHasFixedSize(true);

        if (savedInstance == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                idMovie = null;
                movieTitle = null;
                releaseDate = null;
                posterPath = null;
                voteAverage = null;
                overview = null;
            } else {
                idMovie = extras.getString("idMovie");
                movieTitle = extras.getString("movieTitle");
                releaseDate = extras.getString("releaseDate");
                posterPath = extras.getString("posterPath");
                voteAverage = extras.getString("voteAverage");
                overview = extras.getString("overview");
            }
        } else {
            idMovie = (String) savedInstance.getSerializable("idMovie");
            movieTitle = (String) savedInstance.getSerializable("movieTitle");
            releaseDate = (String) savedInstance.getSerializable("releaseDate");
            posterPath = (String) savedInstance.getSerializable("posterPath");
            voteAverage = (String) savedInstance.getSerializable("voteAverage");
            overview = (String) savedInstance.getSerializable("overview");

        }

        loadContent(idMovie);

        TextView movieTitle = (TextView) findViewById(R.id.movieTitle);
        TextView releaseDate = (TextView) findViewById(R.id.releaseDate);
        SimpleDraweeView posterPath = (SimpleDraweeView) findViewById(R.id.posterPath);
        TextView voteAverage = (TextView) findViewById(R.id.voteAverage);
        TextView overview = (TextView) findViewById(R.id.overview);
        movieTitle.setText(this.movieTitle);
        String strCurrentDate = this.releaseDate;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date newDate = null;

        try {
            newDate = format.parse(strCurrentDate);
            format = new SimpleDateFormat("MMM/dd/yyyy");
            String date = format.format(newDate);
            releaseDate.setText(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        String IMAGE_BASE_URL = "http://image.tmdb.org/t/p/w185";
        Uri imageUri = Uri.parse(IMAGE_BASE_URL + this.posterPath);
        posterPath.setImageURI(imageUri);
        voteAverage.setText(this.voteAverage);
        overview.setText(this.overview);
    }

    private void loadContent(String idMovie) {
        loadTrailers loadTrailers = new loadTrailers();
        loadTrailers.execute(idMovie);

        loadReviews loadReviews = new loadReviews();
        loadReviews.execute(idMovie);

    }

    public class loadTrailers extends AsyncTask<String, String, String>{

        @Override
        protected String doInBackground(String... params) {

            Gson gson = new GsonBuilder()
                    .registerTypeAdapterFactory(new ItemTypeAdapterFactory()) // This is the important line ;)
                    .setDateFormat("yyyy'-'MM'-'dd'T'HH':'mm':'ss'.'SSS'Z'")
                    .create();

            RestAdapter restAdapter = new RestAdapter.Builder()
                    .setLogLevel(RestAdapter.LogLevel.FULL)
                    .setEndpoint(API_URL)
                    .setConverter(new GsonConverter(gson))
                    .build();

            moviesApi = restAdapter.create(alancastro.com.udacity_popular_movies.api.moviesApi.class);
            moviesApi.getVideos(API_KEY, String.valueOf(idMovie) , new Callback<List<movieTrailerModel>>() {
                @Override
                public void success(List<movieTrailerModel> movieTrailerModel, Response response) {
                    RVMovieTrailerAdapter adapter = new RVMovieTrailerAdapter(movieTrailerModel);
                    rvTrailer.setAdapter(adapter);
                    Log.d("DEBUG",String.valueOf(rvTrailer.getHeight()));
                }

                @Override
                public void failure(RetrofitError error) {
                    Log.i("FAILURE", error.getMessage());
                }
            });

            return null;
        }
    }

    public class loadReviews extends AsyncTask<String, String, String>{

        @Override
        protected String doInBackground(String... params) {

            Gson gson = new GsonBuilder()
                    .registerTypeAdapterFactory(new ItemTypeAdapterFactory()) // This is the important line ;)
                    .setDateFormat("yyyy'-'MM'-'dd'T'HH':'mm':'ss'.'SSS'Z'")
                    .create();

            RestAdapter restAdapter = new RestAdapter.Builder()
                    .setLogLevel(RestAdapter.LogLevel.FULL)
                    .setEndpoint(API_URL)
                    .setConverter(new GsonConverter(gson))
                    .build();

            moviesApi = restAdapter.create(alancastro.com.udacity_popular_movies.api.moviesApi.class);
            moviesApi.getReviews(API_KEY, String.valueOf(idMovie), new Callback<List<movieReviewModel>>() {
                @Override
                public void success(List<movieReviewModel> movieReviewModel, Response response) {
                    RVMovieReviewAdapter adapter = new RVMovieReviewAdapter(movieReviewModel);
                    rvReview.setAdapter(adapter);
                    Log.d("DEBUG", String.valueOf(rvReview.getHeight()));
                }

                @Override
                public void failure(RetrofitError error) {
                    Log.i("FAILURE", error.getMessage());
                }
            });

            return null;
        }
    }

}
