package com.example.flixster.Models;

import android.graphics.Color;
import android.util.Log;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Headers;

@Parcel
public class Movie {

    int color;
    int id;
    String backdropPath;
    String posterPath;
    String title;
    String overview;
    Double voteAverage;
    String videoUrl;

    public Movie() {}

    public Movie(JSONObject jsonObject, int index) throws JSONException {
        backdropPath = jsonObject.getString("backdrop_path");
        posterPath = jsonObject.getString("poster_path");
        title = jsonObject.getString("title");
        overview = jsonObject.getString("overview");
        int darkGray = Color.parseColor("#dbdbdb");
        int lightGray = Color.parseColor("#f0f0f0");
        color = (index % 2 == 0) ? darkGray : lightGray;
        voteAverage = jsonObject.getDouble("vote_average");
        id = jsonObject.getInt("id");
        getUrl(id);
    }

    public int getId() {
        return id;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    private void getUrl(int id) {
        String url = "https://api.themoviedb.org/3/movie/" + Integer.toString(id) + "/videos?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed";
        AsyncHttpClient client =  new AsyncHttpClient();
        client.get(url, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {
                Log.d("Youtube", "Link Success!");
                JSONObject jsonObject = json.jsonObject;
                try {
                    JSONArray results = jsonObject.getJSONArray("results");
                    JSONObject video = results.getJSONObject(0);
                    videoUrl = video.getString("key");
                } catch (JSONException e) {
                    videoUrl = "";
                    Log.d("Youtube", "id Doesn't Exist!");
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                Log.d("Youtube", "Link Failure!");
            }
        });
    }

    public static List<Movie> fromJsonArray(JSONArray movieJsonArray) throws JSONException {
        List<Movie> movies = new ArrayList<>();
        for (int i = 0; i < movieJsonArray.length();i++) {
            movies.add(new Movie(movieJsonArray.getJSONObject(i),i));
        }
        return movies;
    }

    public String getBackdropPath() {
        return String.format("https://image.tmdb.org/t/p/w342/%s",backdropPath);
    }

    public String getPosterPath() {
        return String.format("https://image.tmdb.org/t/p/w342/%s",posterPath);
    }

    public int getColor() {return color;}

    public String getTitle() {
        return title;
    }

    public String getOverview() {
        return overview;
    }

    public Double getVoteAverage() { return voteAverage; }
}
