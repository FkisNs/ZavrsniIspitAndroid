package com.ftninformatika.zavrsniispitandroid.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.ftninformatika.zavrsniispitandroid.R;
import com.ftninformatika.zavrsniispitandroid.adapter.MojRecyclerViewAdapter;

import java.io.Serializable;

public class MainActivity extends AppCompatActivity implements Serializable,
        MojRecyclerViewAdapter.OnRecyclerItemClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onRVItemClick(Search search) {

    }
}
