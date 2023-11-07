package com.example.room_demorx;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.example.room_demorx.Data.DiaryDatabase;
import com.example.room_demorx.Model.Diary;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableCompletableObserver;
import io.reactivex.schedulers.Schedulers;

public class MainFragment extends Fragment  {
    public MainFragment() {

    }

    RecyclerView recyclerView;
    DiariesListAdapter diariesListAdapter;
    List<Diary> diaries;
    FloatingActionButton newDiary;
    DiaryDatabase db;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        diaries = new ArrayList<>();
        newDiary = view.findViewById(R.id.btn_new);

        recyclerView = view.findViewById(R.id.rv_diaries);
        Log.i("messy", "onCreateView: " + recyclerView);
        diariesListAdapter = new DiariesListAdapter(position -> {
            // this method will handle the onclick options click
            performOptionsMenuClick(position,view);
        });

        recyclerView.setAdapter(diariesListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        db = (DiaryDatabase) Room.databaseBuilder(view.getContext(), DiaryDatabase.class, "myDiaryDatabase").build();

        //insert default diaries
        /*


        diaries.add((new Diary("trip to Madrid", "2023/04/18","I liked that trip, Never went there tho hhhh")));
        diaries.add((new Diary("trip to Biarritz", "2023/09/08","Truly a magical city, I wouldn't wanna go there one more time")));
        diaries.add((new Diary("trip to Bedous", "2023/05/01","a small but cozy and huge green mountains village")));
        diaries.add((new Diary("trip to bordeaux", "2023/01/18","liked that city it kinda reminded me of my home city")));
        diaries.add((new Diary("trip to Archachon", "2023/09/08","on of the most beautiful beaches I've seen, calm, beautiful city center as well")));
        diaries.add((new Diary("trip to tarbes", "2023/05/01","neutral about it, gone to some places , silence is the word I'd use to describe it")));
        diaries.add((new Diary("trip to Anglet", "2023/01/18","beautiful and typical sands, its beaches feel like freedom")));

        //inserting with an Insert method

        db.getdiaryDAO().insertAll(diaries)
                .subscribeOn(Schedulers.io())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onComplete() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });

 */

        loadDiaries();
        newDiary.setOnClickListener(view1 -> {
            Navigation.findNavController(view1).navigate(R.id.action_mainFragment_to_detailsFragment);

        });

        registerForContextMenu(recyclerView);

        return view;
    }

    private void performOptionsMenuClick(int position,View view) {

            // create object of PopupMenu and pass context and view where we want
            // to show the popup menu
            PopupMenu popupMenu = new PopupMenu(getContext() , view.findViewById(R.id.itemCardView));
            // add the menu
            popupMenu.inflate(R.menu.recyclerview_item_context_menu);
            // implement on menu item click Listener
            popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener(){
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    CharSequence title = item.getTitle();
                    if ("Edit".contentEquals(title)) {// here are the logic to delete an item from the list
                        Bundle bundle = new Bundle();
                        bundle.putString("id", String.valueOf(diaries.get(diariesListAdapter.currentPosition).id));
                        bundle.putString("title", diaries.get(diariesListAdapter.currentPosition).title);
                        bundle.putString("body", diaries.get(diariesListAdapter.currentPosition).body);
                        Navigation.findNavController(requireView()).navigate(R.id.action_mainFragment_to_detailsFragment, bundle);
                        return true;
                        // in the same way you can implement others
                    } else if ("Delete".contentEquals(title)) {// define
                        deleteClickedDiary();
                        return true;
                    }
                    return false;
                }
            });
            popupMenu.show();
        }



    void loadDiaries() {
        Log.i("messy", "inside loadDiaries() ");

        db.getdiaryDAO().getAll().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(o -> {
            diaries = o;
            diariesListAdapter.setDiaries(diaries);
        }, e -> {
            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
        });
    }



    public void deleteClickedDiary() {
        db.getdiaryDAO().delete(diaries.get(diariesListAdapter.currentPosition)).subscribeOn(Schedulers.io()).subscribe(new DisposableCompletableObserver() {
            @Override
            public void onComplete() {
                loadDiaries();
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }
        });


    }



    // need to deal with :

    //long click delete option
    //rv_item backgroung color
    // add current date
    // the edittext problem
    // dynamically change the visibility of saveDiary button

}
