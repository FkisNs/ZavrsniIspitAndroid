package com.ftninformatika.zavrsniispitandroid.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
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
    TextView year;
    TextView rated;
    TextView runtime;
    TextView genre;
    TextView director;
    TextView writer;
    TextView actors;
    TextView country;
    TextView awards;
    TextView metaScore;
    TextView imdbRating;
    TextView imdbVotes;
    TextView imdbId;
    TextView type;
    TextView dvd;
    TextView boxOffice;
    TextView production;
    TextView website;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_movie);
        setupToolbar();

        tv_plot = findViewById(R.id.tv_plot);
        textViewTitle = findViewById(R.id.tv_ime_f_detalji_filma);
        imageView = findViewById(R.id.iv_f_detalji_filma);
        year = findViewById(R.id.tv_year);
        rated = findViewById(R.id.tv_rated);
        runtime = findViewById(R.id.tv_runtime);
        genre = findViewById(R.id.tv_genre);
        director = findViewById(R.id.tv_director);
        writer = findViewById(R.id.tv_writer);
        actors = findViewById(R.id.tv_actors);
        country = findViewById(R.id.tv_country);
        awards = findViewById(R.id.tv_awards);
        metaScore = findViewById(R.id.tv_metascore);
        imdbRating = findViewById(R.id.tv_imdb_rating);
        imdbVotes = findViewById(R.id.tv_imdb_votes);
        imdbId = findViewById(R.id.tv_imdb_id);
        type = findViewById(R.id.tv_type);
        dvd = findViewById(R.id.tv_dvd);
        boxOffice = findViewById(R.id.tv_boxoffice);
        production = findViewById(R.id.tv_production);
        website = findViewById(R.id.tv_website);

        //ovde sam poslao ceo objekat search preko intenta.
        Search search = (Search) getIntent().getSerializableExtra("search");
        String movie_id = search.getImdbID();

        callService(movie_id);
    }

    //Pocetak setup-a za toolbar
    // action bar prikazuje opcije iz meni.xml
    //uneti u action main.xml AppBarLayout i onda Toolbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    // onOptionsItemSelected method is called whenever an item in the Toolbar is selected.
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                Intent intent = new Intent(this,SettingsActivity.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    //setuje toolbar
    private void setupToolbar(){
        toolbar = findViewById(R.id.toolbar_detail);
        setSupportActionBar(toolbar);
        final android.support.v7.app.ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_action_hamburger);
            actionBar.setHomeButtonEnabled(true);
            actionBar.show();
        }
    }
    //kraj setup-a za toolbar

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
                    year.setText(movie.getYear());
                    rated.setText(movie.getRated());
                    runtime.setText(movie.getRuntime());
                    genre.setText(movie.getGenre());
                    director.setText(movie.getDirector());
                    writer.setText(movie.getWriter());
                    actors.setText(movie.getActors());
                    country.setText(movie.getCountry());
                    awards.setText(movie.getAwards());
                    metaScore.setText(movie.getMetascore());
                    imdbRating.setText(movie.getImdbRating());
                    imdbVotes.setText(movie.getImdbVotes());
                    imdbId.setText(movie.getImdbID());
                    type.setText(movie.getType());
                    dvd.setText(movie.getDVD());
                    boxOffice.setText(movie.getBoxOffice());
                    production.setText(movie.getProduction());
                    website.setText(movie.getWebsite());
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
