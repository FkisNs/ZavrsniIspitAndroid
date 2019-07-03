package com.ftninformatika.zavrsniispitandroid.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ftninformatika.zavrsniispitandroid.R;
import com.ftninformatika.zavrsniispitandroid.adapter.MojRecyclerViewAdapter;
import com.ftninformatika.zavrsniispitandroid.model.Search;
import com.ftninformatika.zavrsniispitandroid.model.SearchResult;
import com.ftninformatika.zavrsniispitandroid.net.OMDApiService;

import java.io.Serializable;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements Serializable,
        MojRecyclerViewAdapter.OnRecyclerItemClickListener {

    RecyclerView rvLista;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupToolbar();

        rvLista = findViewById(R.id.rv_lista);

        final EditText query = findViewById(R.id.searchText);

        Button searchBtn = findViewById(R.id.searchBtn);

        //klikom na dugme za trazenje filma poziva se metoda callService sa parametrom koji je uneo korisnik
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Unese Batman -> query.getText().toString().trim()
                callService(query.getText().toString().trim());
            }
        });

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
        toolbar = findViewById(R.id.toolbar);
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
        queryParams.put("s", query);

        Call<SearchResult> call = OMDApiService.apiInterface().searchOMDB(queryParams);
        call.enqueue(new Callback<SearchResult>() {
            @Override
            public void onResponse(Call<SearchResult> call, Response<SearchResult> response) {
                if (response.code() == 200){
                    SearchResult resp = response.body();
                    rvLista.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                    rvLista.setAdapter(
                            new MojRecyclerViewAdapter(resp.getSearch(),MainActivity.this)
                    );
                }else{
                    Toast.makeText(MainActivity.this, "Greska pri ucitavanju je:" + response.code(),
                            Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<SearchResult> call, Throwable t) {
                Toast.makeText(MainActivity.this,"Greska: "+t.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }

    //    interfejs za komunikaciju RecyclerView i MainActivity
    @Override
    public void onRVItemClick(Search search) {
        Intent intent = new Intent(MainActivity.this, DetailMovie.class);
        //saljmo ceo objekat preko intenta tako sto se implementira interfejs Serializable u MainActivity i
        //u klasu tog objekta koji saljemo
        intent.putExtra("search",search);

        startActivity(intent);
    }
}
