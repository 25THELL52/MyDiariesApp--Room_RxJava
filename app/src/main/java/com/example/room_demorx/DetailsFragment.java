package com.example.room_demorx;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.room.Room;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.example.room_demorx.Data.DiaryDatabase;
import com.example.room_demorx.Model.Diary;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import io.reactivex.CompletableObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


public class DetailsFragment extends Fragment {

    EditText diarytitle, diarybody;
    FloatingActionButton saveDiary;
    boolean updateState;


    public DetailsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_details, container, false);
        diarytitle = view.findViewById(R.id.diarytitledetailsfragmentedt);
        diarybody = view.findViewById(R.id.diarybodydetailsfragmentedt);
        saveDiary= view.findViewById(R.id.save_btn);
        if(getArguments()!=null){

            fillInDetails(getArguments().getString("title"),getArguments().getString("body"));
            updateState=true;
        }

        else {diarybody.requestFocus();
        updateState=false;}



        DiaryDatabase db = (DiaryDatabase) Room.databaseBuilder(view.getContext(),
                DiaryDatabase.class, "myDiaryDatabase").build();
        saveDiary.setOnClickListener(view1 -> {
            if(!updateState){db.getdiaryDAO().insert(new Diary(diarytitle.getText().toString(),"02/02/2023",diarybody.getText().toString()))
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
                   });}
            else {db.getdiaryDAO().updateDiary(Integer.parseInt(getArguments().getString("id")),diarybody.getText().toString())
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
                    });}

        });



        return view;
    }

    public  void fillInDetails(String title,String body){

        diarytitle.setText(title);
        diarybody.setText(body);

    }




}