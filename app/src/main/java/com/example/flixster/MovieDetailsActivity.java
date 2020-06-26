package com.example.flixster;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.example.flixster.Models.Movie;

import org.parceler.Parcels;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;


public class MovieDetailsActivity extends AppCompatActivity  {

    Movie movie;

    TextView tvTitle;
    TextView tvOverview;
    RatingBar rbVoteAverage;
    ImageView ivBackground;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        tvTitle = (TextView) findViewById(R.id.tvTitle);
        tvOverview = (TextView) findViewById(R.id.tvOverview);
        rbVoteAverage = (RatingBar) findViewById(R.id.rbVoteAverage);
        ivBackground = (ImageView) findViewById(R.id.ivBackground);

        movie = (Movie) Parcels.unwrap(getIntent().getParcelableExtra(Movie.class.getSimpleName()));
        Log.d("MovieDetailsActivity", String.format("Showing details for '%s'", movie.getTitle()));

        tvTitle.setText(movie.getTitle());
        tvOverview.setText(movie.getOverview());
        float voteAverage = movie.getVoteAverage().floatValue();
        rbVoteAverage.setRating(voteAverage = voteAverage > 0 ? voteAverage / 2.0f : voteAverage);
        Glide
                .with(this)
                .load(movie.getBackdropPath())
                .fitCenter()
                .transform(new RoundedCornersTransformation(30,10))
                .placeholder(R.drawable.flicks_backdrop_placeholder)
                .error(R.drawable.icon)
                .into(ivBackground);
    }

    public void onClick(View view) {
        String videoUrl = movie.getVideoUrl();
        if (videoUrl.equals("")) {
            Toast.makeText(this, "No available trailers to display", Toast.LENGTH_SHORT).show();
        } else {
            Intent intent = new Intent(this, MovieTrailerActivity.class);
            intent.putExtra(Movie.class.getSimpleName(), Parcels.wrap(movie));
            this.startActivity(intent);
        }
    }



}