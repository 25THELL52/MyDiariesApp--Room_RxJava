package com.example.room_demorx;

import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
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
    private OptionsMenuClickListener optionsMenuClickListener;

    // create an interface for onClickListener
    // so that we can handle data most effectively in MainActivity.kt
    interface OptionsMenuClickListener {
        void onOptionsMenuClicked(int position);
    }
    int currentPosition ;

    DiariesListAdapter(OptionsMenuClickListener optionsMenuClickListener)
    {
        this.optionsMenuClickListener= optionsMenuClickListener;
    }

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




    });


    holder.itemView.setOnLongClickListener(

            view -> {
                optionsMenuClickListener.onOptionsMenuClicked(position);
                currentPosition = holder.getAbsoluteAdapterPosition();
                Log.e("message", "long Clicked");
                return true;
            }

    );}

    @Override
    public int getItemCount() {

        return diaries.size();
    }

    @Override
    public void onViewRecycled(@NonNull DiariesListViewHolder holder) {
        holder.itemView.setOnLongClickListener(null);
        super.onViewRecycled(holder);
    }

    public static class DiariesListViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {

        TextView title,date,body;
        public DiariesListViewHolder(@NonNull View itemView) {
            super(itemView);
            body= (TextView) itemView.findViewById(R.id.body);

            title= (TextView)itemView.findViewById(R.id.diarytitle);
            date= (TextView)itemView.findViewById(R.id.date);
            itemView.setOnCreateContextMenuListener(this);


        }

        @Override
        public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
            contextMenu.add(0, view.getId(), 0, "Edit");
            contextMenu.add(0, view.getId(), 0, "Delete");

        }
    }
}
