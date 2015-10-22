package alancastro.com.udacity_popular_movies;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import alancastro.com.udacity_popular_movies.model.movieModel;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.converter.GsonConverter;

/**
 * Created by alannnc on 2/10/15.
 */
public class PopularMoviesActivity extends Activity {
    private static final String API_URL = "http://api.themoviedb.org/3";
    private static final String API_KEY = "6c586439d08f9cfbc64f4e5a92aa1408";
    private RecyclerView rv;
    private GridLayoutManager gridLayoutManager;
    private alancastro.com.udacity_popular_movies.api.moviesApi moviesApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setBackgroundDrawable(null);
        setContentView(R.layout.popular_movies_activity);
        Fresco.initialize(this);

        setUpSpinner();

        rv = (RecyclerView)findViewById(R.id.rv);
        rv.setHasFixedSize(true);

        gridLayoutManager = new GridLayoutManager(this, 2);
        rv.setLayoutManager(gridLayoutManager);
        rv.setHasFixedSize(true);

    }

    private void setUpSpinner(){
        Spinner spinner = (Spinner) findViewById(R.id.spinner_nav);
        // Create an ArrayAdapter using the string array and a default spinner layout
        final ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.sort_movies, R.layout.spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                Log.d("Spinner Clicked", String.valueOf(position));
                if(position == 1){
                    rv.setAdapter(null);
                    BackgroundTask task = new BackgroundTask();
                    task.execute("Highest_Rated");
                }else{
                    rv.setAdapter(null);
                    BackgroundTask task = new BackgroundTask();
                    task.execute("Popular");
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }

        });
    }

    private class BackgroundTask extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {

        }

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
            if (params[0].toString() == "Highest_Rated") {
                moviesApi.getHighestRated(API_KEY, new Callback<List<movieModel>>() {
                    @Override
                    public void success(List<movieModel> movieModel, Response response) {
                        RVMovieAdapter adapter = new RVMovieAdapter(movieModel);
                        rv.setAdapter(adapter);
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Log.i("FAILURE", error.getMessage());
                    }
                });
            } else {
                moviesApi.getPopular(API_KEY, new Callback<List<movieModel>>() {
                    @Override
                    public void success(List<movieModel> movieModel, Response response) {
                        RVMovieAdapter adapter = new RVMovieAdapter(movieModel);
                        rv.setAdapter(adapter);
                        rv.scrollToPosition(1000);
                        rv.invalidateItemDecorations();
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Log.i("FAILURE", error.getMessage());
                    }
                });
            }

            return "success";
        }

        @Override
        protected void onPostExecute(String movies) {
            Log.i("DEBUG", movies);
        }
    }

}
