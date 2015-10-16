package alancastro.com.udacity_popular_movies;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;

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
    private static final String API_KEY = "YOUR_API_KEY";
    private RecyclerView rv;
    private StaggeredGridLayoutManager gaggeredGridLayoutManager;
    private alancastro.com.udacity_popular_movies.api.moviesApi moviesApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setBackgroundDrawable(null);
        setContentView(R.layout.popular_movies_activity);
        Fresco.initialize(this);

        rv = (RecyclerView)findViewById(R.id.rv);
        rv.setHasFixedSize(true);

        gaggeredGridLayoutManager = new StaggeredGridLayoutManager(2, 1);
        rv.setLayoutManager(gaggeredGridLayoutManager);


        //rv=(RecyclerView)findViewById(R.id.rv);

        //LinearLayoutManager llm = new LinearLayoutManager(this);
        //rv.setLayoutManager(llm);
        //rv.setHasFixedSize(true);

        BackgroundTask task = new BackgroundTask();
        task.execute();

    }


    private class BackgroundTask extends AsyncTask<Void, Void, String> {

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected String doInBackground(Void... params) {
            Gson gson = new GsonBuilder()
                    .registerTypeAdapterFactory(new ItemTypeAdapterFactory()) // This is the important line ;)
                    .setDateFormat("yyyy'-'MM'-'dd'T'HH':'mm':'ss'.'SSS'Z'")
                    .create();

            RestAdapter restAdapter = new RestAdapter.Builder()
                    .setLogLevel(RestAdapter.LogLevel.FULL)
                    .setEndpoint(API_URL)
                    .setConverter(new GsonConverter(gson))
                    .build();

            moviesApi= restAdapter.create(alancastro.com.udacity_popular_movies.api.moviesApi.class);

            moviesApi.getFeed(API_KEY, new Callback<List<movieModel>>() {
                @Override
                public void success(List<movieModel> gitmodel, Response response) {
                    RVMovieAdapter adapter = new RVMovieAdapter(gitmodel);
                    rv.setAdapter(adapter);
                }

                @Override
                public void failure(RetrofitError error) {
                    Log.i("FAILURE", error.getMessage());
                }
            });

            return "success";
        }

        @Override
        protected void onPostExecute(String movies) {
            Log.i("DEBUG", movies);
        }
    }

}
