package com.ftninformatika.zavrsniispitandroid.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ftninformatika.zavrsniispitandroid.R;
import com.ftninformatika.zavrsniispitandroid.model.Search;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MojRecyclerViewAdapter extends
        RecyclerView.Adapter<MojRecyclerViewAdapter.MyViewHolder> {

    public List<Search> listaFilmova;
    public OnRecyclerItemClickListener listener;

    public interface OnRecyclerItemClickListener {
        void onRVItemClick(Search search);
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tvYear;
        TextView tvTitle;
        View view;

        public MyViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            tvTitle = itemView.findViewById(R.id.tv_recycler_title);
            tvYear = itemView.findViewById(R.id.tv_recycler_year);
        }

        public void bind(final Search search, final OnRecyclerItemClickListener listener) {
            tvTitle.setText(search.getTitle());
            tvYear.setText(search.getYear());
            ImageView imageView = itemView.findViewById(R.id.imageView_RW);
            Picasso.get().load(search.getPoster()).into(imageView);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onRVItemClick(search);
                }
            });
        }
    }

    public MojRecyclerViewAdapter(List<Search> listaFilmova, OnRecyclerItemClickListener listener) {
        this.listaFilmova = listaFilmova;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.rv_single_item, viewGroup, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, int i) {
        myViewHolder.bind(listaFilmova.get(i), listener);

        int currentPostion = i;
        final Search search = listaFilmova.get(currentPostion);

    }

    @Override
    public int getItemCount() {
        return listaFilmova.size();
    }

}
