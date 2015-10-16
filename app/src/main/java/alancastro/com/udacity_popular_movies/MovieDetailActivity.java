package alancastro.com.udacity_popular_movies;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

/**
 * Created by alannnc on 9/10/15.
 */
public class MovieDetailActivity extends Activity {

    private String IMAGE_BASE_URL = "http://image.tmdb.org/t/p/w185";

    private String movieTitle;
    private String releaseDate;
    private String posterPath;
    private int voteAverage;
    private String overview;

    @Override
    public void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);

        setContentView(R.layout.activity_movie_detail);

        if (savedInstance == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                movieTitle = null;
                releaseDate = null;
                posterPath = null;
                voteAverage = -1;
                overview = null;
            } else {
                movieTitle = extras.getString("movieTitle");
                releaseDate = extras.getString("releaseDate");
                posterPath = extras.getString("posterPath");
                voteAverage = Integer.parseInt(extras.getString("voteAverage"));
                overview = extras.getString("overview");
            }
        } else {
            movieTitle = (String) savedInstance.getSerializable("movieTitle");
            releaseDate = (String) savedInstance.getSerializable("releaseDate");
            posterPath = (String) savedInstance.getSerializable("posterPath");
            voteAverage = (int) savedInstance.getSerializable("voteAverage");
            overview = (String) savedInstance.getSerializable("overview");

        }
        TextView movieTitle = (TextView) findViewById(R.id.movieTitle);
        TextView releaseDate = (TextView) findViewById(R.id.releaseDate);
        SimpleDraweeView posterPath = (SimpleDraweeView) findViewById(R.id.posterPath);
        TextView voteAverage = (TextView) findViewById(R.id.voteAverage);
        TextView overview = (TextView) findViewById(R.id.overview);
        movieTitle.setText(this.movieTitle);
        releaseDate.setText(this.releaseDate);
        Uri imageUri = Uri.parse(IMAGE_BASE_URL + this.posterPath);
        posterPath.setImageURI(imageUri);
        voteAverage.setText(String.valueOf(this.voteAverage));
        overview.setText(this.overview);
    }

}
