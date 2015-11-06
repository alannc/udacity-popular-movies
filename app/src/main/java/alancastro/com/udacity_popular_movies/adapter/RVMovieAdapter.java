package alancastro.com.udacity_popular_movies.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

import alancastro.com.udacity_popular_movies.MovieDetailActivity;
import alancastro.com.udacity_popular_movies.R;
import alancastro.com.udacity_popular_movies.model.movieModel;

/**
 * Created by alannnc on 5/10/15.
 */
public class RVMovieAdapter extends RecyclerView.Adapter<RVMovieAdapter.MovieViewHolder>{
    private String IMAGE_BASE_URL = "http://image.tmdb.org/t/p/w185";

    List<movieModel> movies;

    public RVMovieAdapter(List<movieModel> movies){
        this.movies = movies;
    }


    public static class MovieViewHolder extends RecyclerView.ViewHolder{

        CardView cv;
        TextView movieName;
        SimpleDraweeView moviePoster;
        String idMovie;
        String movieTitle;
        String releaseDate;
        String posterPath;
        String voteAverage;
        String overview;
        private final Context context;

        public MovieViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent myIntent = new Intent(context, MovieDetailActivity.class);
                    myIntent.putExtra("idMovie", idMovie);
                    myIntent.putExtra("movieTitle", movieTitle); //Optional parameters
                    myIntent.putExtra("releaseDate", releaseDate); //Optional parameters
                    myIntent.putExtra("movieTitle", movieTitle); //Optional parameters
                    myIntent.putExtra("posterPath", posterPath); //Optional parameters
                    myIntent.putExtra("voteAverage", voteAverage); //Optional parameters
                    myIntent.putExtra("overview", overview); //Optional parameters
                    context.startActivity(myIntent);
                }
            });
            cv = (CardView)itemView.findViewById(R.id.cv);
            movieName = (TextView)itemView.findViewById(R.id.person_name);
            moviePoster = (SimpleDraweeView)itemView.findViewById(R.id.person_photo);
            context = itemView.getContext();
        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item, viewGroup, false);
        MovieViewHolder pvh = new MovieViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(final MovieViewHolder movieViewHolder, int i) {
        Uri imageUri = Uri.parse(IMAGE_BASE_URL + movies.get(i).getPosterPath());
        movieViewHolder.idMovie = String.valueOf(movies.get(i).getId());
        movieViewHolder.movieName.setText(movies.get(i).getTitle());
        movieViewHolder.moviePoster.setImageURI(imageUri);
        movieViewHolder.movieTitle = movies.get(i).getTitle();
        movieViewHolder.releaseDate = movies.get(i).getReleaseDate();
        movieViewHolder.posterPath = movies.get(i).getPosterPath();
        movieViewHolder.voteAverage = movies.get(i).getVoteAverage().toString();
        movieViewHolder.overview = movies.get(i).getOverview();
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

}


