package com.example.room_demorx;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

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
import io.reactivex.schedulers.Schedulers;
public class MainFragment extends Fragment {
    public MainFragment() {

    }

    RecyclerView recyclerView;
    DiariesListAdapter diariesListAdapter;
    List<Diary> diaries;
    FloatingActionButton newDiary;
    DiaryDatabase db;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        diaries = new ArrayList<>();
        newDiary = view.findViewById(R.id.btn_new);

        recyclerView = view.findViewById(R.id.rv_diaries);
        Log.i("messy", "onCreateView: " + recyclerView);
        diariesListAdapter = new DiariesListAdapter();
        recyclerView.setAdapter(diariesListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        db = (DiaryDatabase) Room.databaseBuilder(view.getContext(),
                DiaryDatabase.class, "myDiaryDatabase").build();

        //insert test
        /*
       diaries.add((new Diary("title1", "2023/09/08","weirdich")));
        diaries.add((new Diary("title2", "2023/09/08","weirdich")));

        diaries.add((new Diary("trip to Madrid", "2023/04/18","I liked that trip, Never went there tho hhhh")));
        diaries.add((new Diary("trip to Biarritz", "2023/09/08","Truly a magical city, I wouldn't wanna go there one more time")));
        diaries.add((new Diary("trip to Bedous", "2023/05/01","a small but cozy and huge green mountains village")));
        diaries.add((new Diary("trip to bordeaux", "2023/01/18","liked that city it kinda reminded me of my home city")));
        diaries.add((new Diary("trip to Archachon", "2023/09/08","on of the most beautiful beaches I've seen, calm, beautiful city center as well")));
        diaries.add((new Diary("trip to tarbes", "2023/05/01","neutral about it, gone to some places , silence is the word I'd use to describe it")));
        diaries.add((new Diary("trip to Anglet", "2023/01/18","beautiful and typical sands, its beaches feel like freedom")));
        diaries.add((new Diary("trip to Lourdes", "2023/09/08","weirdich")));

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

        //deleting with a Query method title="title2"
        /*
        db.getdiaryDAO().delete("title2").subscribeOn(Schedulers.io())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                        //get the diaries list and set it as the adapter's list;

                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });
        ;


         */

        loadDiaries();
        newDiary.setOnClickListener(view1 -> {
            Navigation.findNavController(view1).navigate(R.id.action_mainFragment_to_detailsFragment);

        });


        return view;
    }

    void loadDiaries() {
        Log.i("messy", "inside loadDiaries() ");

        db.getdiaryDAO().getAll()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(o -> {
                    diaries = o;
                    diariesListAdapter.setDiaries(diaries);
                }, e ->
                {
                    Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }



    // need to deal with :

    //long click delete option
    //rv_item backgroung color
    // add current date
    // the edittext problem
    // dynamically change the visibility of saveDiary button

}
