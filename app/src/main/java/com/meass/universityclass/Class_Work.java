package com.meass.universityclass;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Class_Work extends AppCompatActivity {
    LottieAnimationView empty_cart;
    DocumentReference documentReference;
    RecyclerView recyclerView;
    AnswerAdapter getDataAdapter1;
    List<AnswerModel> getList;
    String url;
    private HashMap<String, String> user;
    private String name, section, roomid, class_,uuid;
    FloatingActionButton fab_plus;
    FirebaseAuth firebaseAuth,mAuth;
    FirebaseFirestore  firebaseFirestore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class__work);
        FirebaseApp.initializeApp(Class_Work.this);
        try {
            name=getIntent().getStringExtra("name");
            section=getIntent().getStringExtra("section");
            roomid=getIntent().getStringExtra("roomid");
            class_=getIntent().getStringExtra("class_");
            uuid=getIntent().getStringExtra("uuid");



        }catch (Exception e) {
            name=getIntent().getStringExtra("name");
            section=getIntent().getStringExtra("section");
            roomid=getIntent().getStringExtra("roomid");
            class_=getIntent().getStringExtra("class_");
            uuid=getIntent().getStringExtra("uuid");
        }
        Toolbar toolbar = findViewById(R.id.toolbar);
        mAuth=FirebaseAuth.getInstance();
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseFirestore=FirebaseFirestore.getInstance();

        toolbar.setTitle("Class Work");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setElevation(10.0f);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();


        getList = new ArrayList<>();
        getDataAdapter1 = new AnswerAdapter(getList,"as");
        firebaseFirestore = FirebaseFirestore.getInstance();
        documentReference =      firebaseFirestore.collection("Answer").document("List").collection(roomid)

                .document();
        recyclerView = findViewById(R.id.rreeeed);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(Class_Work.this));
        recyclerView.setAdapter(getDataAdapter1);
        reciveData();

    }
    private void reciveData() {

        firebaseFirestore.collection("Answer").document("List").collection(roomid)

                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                        for (DocumentChange ds : queryDocumentSnapshots.getDocumentChanges()) {
                            if (ds.getType() == DocumentChange.Type.ADDED) {

                 /*String first;
                 first = ds.getDocument().getString("name");
                 Toast.makeText(MainActivity2.this, "" + first, Toast.LENGTH_SHORT).show();*/
                                AnswerModel get = ds.getDocument().toObject(AnswerModel.class);
                                getList.add(get);
                                getDataAdapter1.notifyDataSetChanged();
                            }

                        }
                    }
                });

    }
    @Override
    public boolean onSupportNavigateUp() {
        Intent intent=new Intent(getApplicationContext(),MyClassHome.class);
        intent.putExtra("name",name);
        intent.putExtra("section",section);
        intent.putExtra("roomid",roomid);
        intent.putExtra("class_",class_);
        intent.putExtra("uuid",uuid);
        startActivity(intent);

        return true;
    }

    @Override
    public void onBackPressed() {
        Intent intent=new Intent(getApplicationContext(),MyClassHome.class);
        intent.putExtra("name",name);
        intent.putExtra("section",section);
        intent.putExtra("roomid",roomid);
        intent.putExtra("class_",class_);
        intent.putExtra("uuid",uuid);
        startActivity(intent);
    }
}