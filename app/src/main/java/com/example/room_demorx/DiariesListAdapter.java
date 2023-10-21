package com.example.room_demorx;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.room_demorx.Model.Diary;

import java.util.ArrayList;
import java.util.List;

public class DiariesListAdapter extends RecyclerView.Adapter<DiariesListAdapter.DiariesListViewHolder> {


    List<Diary> diaries = new ArrayList<>();

    public void setDiaries(List<Diary> diaries) {
        this.diaries = diaries;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public DiariesListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.rv_item, parent, false);
        return new DiariesListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DiariesListViewHolder holder, int position) {
        holder.body.setText(diaries.get(position).body);
        holder.date.setText(diaries.get(position).date);

        holder.title.setText(diaries.get(position).title);
        holder.itemView.setOnClickListener( view->{
            Bundle bundle = new Bundle();
            bundle.putString("id",String.valueOf(diaries.get(position).id));
            bundle.putString("title", holder.title.getText().toString());
            bundle.putString("body",holder.body.getText().toString());
            Navigation.findNavController(view).navigate(R.id.action_mainFragment_to_detailsFragment,bundle);




    });}

    @Override
    public int getItemCount() {
        return diaries.size();
    }

    public static class DiariesListViewHolder extends RecyclerView.ViewHolder {

        TextView title,date,body;
        public DiariesListViewHolder(@NonNull View itemView) {
            super(itemView);
            body= (TextView) itemView.findViewById(R.id.body);

            title= (TextView)itemView.findViewById(R.id.diarytitle);
            date= (TextView)itemView.findViewById(R.id.date);


        }
    }
}
