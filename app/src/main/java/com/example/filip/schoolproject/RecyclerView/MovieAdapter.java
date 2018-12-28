package com.example.filip.schoolproject.RecyclerView;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.filip.schoolproject.R;

import java.lang.ref.WeakReference;
import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieHolder>{
    private List<MovieModel> _data;
    private WeakReference<Context> _context;

    public MovieAdapter(List<MovieModel> data, WeakReference<Context> contextReference) {
        _context = contextReference;
        _data = data;
    }

    public void refreshData(List<MovieModel> data){
        _data = data;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MovieHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(_context.get()).inflate(R.layout.movie_item,viewGroup,false);
        return new MovieHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieHolder movieHolder, int i) {

        movieHolder.bus_title.setText(_data.get(i).bus_title);
    }

    @Override
    public int getItemCount() {
        if(_data!=null)
        return _data.size();
        return 0;
    }
}
