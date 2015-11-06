package alancastro.com.udacity_popular_movies.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import alancastro.com.udacity_popular_movies.R;
import alancastro.com.udacity_popular_movies.model.movieTrailerModel;

/**
 * Created by alannnc on 26/10/15.
 */
public class RVMovieTrailerAdapter extends RecyclerView.Adapter<RVMovieTrailerAdapter.TrailerHolder>{

    List<movieTrailerModel> movieTrailers;

    public RVMovieTrailerAdapter(List<movieTrailerModel> movieTrailer){
        this.movieTrailers = movieTrailer;
    }

    public static class TrailerHolder extends RecyclerView.ViewHolder{

        TextView trailerName;
        String trailerVideoKey;
        TextView trailerSite;
        TextView trailerSize;


        public TrailerHolder(View itemView, final Context context) {
            super(itemView);
            trailerName = (TextView) itemView.findViewById(R.id.trailerName);
            trailerSite = (TextView) itemView.findViewById(R.id.trailerSite);
            trailerSize = (TextView) itemView.findViewById(R.id.trailerQuality);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + trailerVideoKey));
                    context.startActivity(intent);
                }
            });
        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public TrailerHolder onCreateViewHolder(ViewGroup parent, int i) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_trailer, parent, false);
        TrailerHolder pvh = new TrailerHolder(v, parent.getContext());
        return pvh;
    }

    @Override
    public void onBindViewHolder(final TrailerHolder holder, int position) {
        holder.trailerName.setText(movieTrailers.get(position).getName());
        holder.trailerVideoKey = (movieTrailers.get(position).getKey());
        holder.trailerSite.setText(movieTrailers.get(position).getSite());
        holder.trailerSize.setText(String.valueOf(movieTrailers.get(position).getSize()) + "p");

    }

    @Override
    public int getItemCount() {
        return movieTrailers.size();
    }


}
