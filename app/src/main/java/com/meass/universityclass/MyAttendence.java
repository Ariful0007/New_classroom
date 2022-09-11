package com.meass.universityclass;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.owater.library.CircleTextView;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class MyAttendence extends AppCompatActivity {
    LottieAnimationView empty_cart;
    DocumentReference documentReference;
    RecyclerView recyclerView;
    AttendenceAdapter getDataAdapter1;
    List<Class_people> getList;
    String url;
    private HashMap<String, String> user;
    private String name, section, roomid, class_,uuid;
    FloatingActionButton fab_plus;
    FirebaseAuth firebaseAuth,mAuth;
    FirebaseFirestore firebaseFirestore;


    Calendar calendar = Calendar.getInstance();
    String current = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());
    String current1 = DateFormat.getDateInstance().format(calendar.getTime());
    CircleTextView circle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_attendence);
        FirebaseApp.initializeApp(MyAttendence.this);
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
        circle=findViewById(R.id.circle);

        toolbar.setTitle("Today's Attendence");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setElevation(10.0f);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();


        getList = new ArrayList<>();
        getDataAdapter1 = new AttendenceAdapter(getList,"as");
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseFirestore.collection(roomid).document(current1).collection("List")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            int ncount = 0;
                            for (DocumentSnapshot document : task.getResult()) {
                                ncount++;
                            }
                            int sum=(ncount*40)/100;
                            circle.setText(""+sum);

                        }
                    }
                });

        documentReference =      firebaseFirestore.collection(roomid).document(current1).collection("List")

                .document();
        recyclerView = findViewById(R.id.rreeeed);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(MyAttendence.this));
        recyclerView.setAdapter(getDataAdapter1);
        reciveData();

    }
    private void reciveData() {

        firebaseFirestore.collection(roomid).document(current1).collection("List")

                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                        for (DocumentChange ds : queryDocumentSnapshots.getDocumentChanges()) {
                            if (ds.getType() == DocumentChange.Type.ADDED) {

                 /*String first;
                 first = ds.getDocument().getString("name");
                 Toast.makeText(MainActivity2.this, "" + first, Toast.LENGTH_SHORT).show();*/
                                Class_people get = ds.getDocument().toObject(Class_people.class);
                                getList.add(get);
                                getDataAdapter1.notifyDataSetChanged();
                            }

                        }
                    }
                });

    }
    @Override
    public boolean onSupportNavigateUp() {
        Intent intent=new Intent(getApplicationContext(),MyJoiningHome.class);
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
        Intent intent=new Intent(getApplicationContext(),MyJoiningHome.class);
        intent.putExtra("name",name);
        intent.putExtra("section",section);
        intent.putExtra("roomid",roomid);
        intent.putExtra("class_",class_);
        intent.putExtra("uuid",uuid);
        startActivity(intent);
    }
}