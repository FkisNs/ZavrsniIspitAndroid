package com.ftninformatika.zavrsniispitandroid.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ftninformatika.zavrsniispitandroid.R;
import com.ftninformatika.zavrsniispitandroid.model.Movie;
import com.ftninformatika.zavrsniispitandroid.model.Search;
import com.ftninformatika.zavrsniispitandroid.net.OMDApiService;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailMovie extends AppCompatActivity {

    TextView tv_plot;
    TextView textViewTitle;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_movie);

        tv_plot = findViewById(R.id.tv_plot);
        textViewTitle = findViewById(R.id.tv_ime_f_detalji_filma);
        imageView = findViewById(R.id.iv_f_detalji_filma);

        //ovde sam poslao ceo objekat search preko intenta.
        Search search = (Search) getIntent().getSerializableExtra("search");
        String movie_id = search.getImdbID();

        callService(movie_id);
    }

    private void callService(String query){
        HashMap<String, String> queryParams = new HashMap<>();
        queryParams.put("apikey", "e6c249d5");
        queryParams.put("i", query); //vraca film za ID koji je uzet iz intenta koji je prosledjen u MainActivity

        Call<Movie> callMovie = OMDApiService.apiInterface().searchMovieOMDB(queryParams);
        callMovie.enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(Call<Movie> call, Response<Movie> response) {
                if (!response.isSuccessful()){
//
                    Toast.makeText(DetailMovie.this, "Greska pri ucitavanju", Toast.LENGTH_SHORT).show();

                }else{
                    Movie movie = response.body();
                    textViewTitle.setText(movie.getTitle());
                    tv_plot.setText(movie.getPlot());
                    Picasso.get().load(movie.getPoster()).into(imageView);
                }
            }

            @Override
            public void onFailure(Call<Movie> call, Throwable t) {
                Toast.makeText(DetailMovie.this,"Greska: "+t.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }
}
