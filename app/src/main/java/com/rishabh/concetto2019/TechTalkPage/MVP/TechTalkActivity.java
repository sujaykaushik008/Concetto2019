package com.rishabh.concetto2019.TechTalkPage.MVP;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rishabh.concetto2019.Authentication.LogInPage.MVP.LoginActivity;
import com.rishabh.concetto2019.Authentication.SignUpPage.MVP.SignupActivity;
import com.rishabh.concetto2019.HomePage.MVP.HomePageActivity;
import com.rishabh.concetto2019.R;
import com.rishabh.concetto2019.TechTalkPage.Model.TechtalkModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

public class TechTalkActivity extends AppCompatActivity implements TechTalkContract.view {

    TechTalkContract.presenter presenter;
    Techtalkadapter techtalkadapter;
    RecyclerView recycler;
    TechtalkModel techtalkModel;
    List<TechtalkModel> list;
    Animation up,down,rotate;
   ProgressDialog progress;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tech_talk_page);
        recycler=findViewById(R.id.tech_recycler_view);
        getSupportActionBar().hide();
        list=new ArrayList<>();
        progress=new ProgressDialog(this);
        progress.setMessage(" Loading Events");
        progress.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progress.setIndeterminate(true);
        progress.setProgress(0);
        progress.show();
        up = AnimationUtils.loadAnimation(this,R.anim.slide_up);
        down = AnimationUtils.loadAnimation(this,R.anim.slide_down);
        rotate = AnimationUtils.loadAnimation(this, R.anim.rotate_button);


        recycler.setHasFixedSize(false);
        recycler.setNestedScrollingEnabled(false);
        recycler.setLayoutManager(new LinearLayoutManager(this));

        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference("Techtalk");

       databaseReference.addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                   for(DataSnapshot db: dataSnapshot.getChildren()){
                       String aboutspeaker = db.child("About Speaker").getValue().toString();
                       String time = db.child("Date").getValue().toString();
                       String date=db.child("Field").getValue().toString();
                       String field=db.child("Location").getValue().toString();
                       String location=db.child("Speaker").getValue().toString();
                       String speaker=db.child("Time").getValue().toString();
                       String name=db.child("EventName").getValue().toString();
                       String url=db.child("image url").getValue().toString();

                       techtalkModel=new TechtalkModel(aboutspeaker,date,field,location,time,name,url);
                       list.add(techtalkModel);

                   }
                   progress.dismiss();
                   techtalkadapter=new Techtalkadapter(list,TechTalkActivity.this,up,down,rotate);
                   techtalkadapter.notifyDataSetChanged();
                   recycler.setAdapter(techtalkadapter);


           }

           @Override
           public void onCancelled(@NonNull DatabaseError databaseError) {

           }
       });


        presenter = new TechTalkPresenter(this);
        ButterKnife.bind(this);
    }

    public void signup(View view) {
        startActivity(new Intent(this, SignupActivity.class));
    }

    public void login(View view) {
        startActivity(new Intent(this, LoginActivity.class));
    }







}
