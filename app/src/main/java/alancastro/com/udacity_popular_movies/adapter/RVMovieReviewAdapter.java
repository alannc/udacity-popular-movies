package alancastro.com.udacity_popular_movies.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import alancastro.com.udacity_popular_movies.R;
import alancastro.com.udacity_popular_movies.model.movieReviewModel;


/**
 * Created by alannnc on 31/10/15.
 */
public class RVMovieReviewAdapter extends RecyclerView.Adapter<RVMovieReviewAdapter.ReviewHolder>{

    List<movieReviewModel> movieReviews;

    public RVMovieReviewAdapter(List<movieReviewModel> movieReview){
        this.movieReviews = movieReview;
    }

    public static class ReviewHolder extends RecyclerView.ViewHolder{

        TextView reviewAuthor;
        TextView reviewContent;

        public ReviewHolder(View itemView) {
            super(itemView);
            reviewAuthor = (TextView) itemView.findViewById(R.id.reviewAuthor);
            reviewContent = (TextView) itemView.findViewById(R.id.reviewContent);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    
                }
            });
        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public ReviewHolder onCreateViewHolder(ViewGroup parent, int i) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_review, parent, false);
        ReviewHolder pvh = new ReviewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(final ReviewHolder holder, int position) {
        holder.reviewAuthor.setText(movieReviews.get(position).getAuthor());
        holder.reviewContent.setText(movieReviews.get(position).getContent());

    }

    @Override
    public int getItemCount() {
        return movieReviews.size();
    }

}
