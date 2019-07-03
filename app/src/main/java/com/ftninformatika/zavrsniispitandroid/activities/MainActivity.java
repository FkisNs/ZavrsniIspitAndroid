package com.ftninformatika.zavrsniispitandroid.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
