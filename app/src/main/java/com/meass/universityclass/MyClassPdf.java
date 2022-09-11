package com.meass.universityclass;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

public class MyClassPdf extends AppCompatActivity {
    RecyclerView recyclerView;

    FirebaseFirestore firebaseFirestore;
    DocumentReference documentReference;

    getDataAdapter1 getDataAdapter1;
    List<PDF_upload> getList;

    FirebaseAuth firebaseAuth;
    EditText username,usernam2e,username3,username4;
    Button loginnn;
    private String name, section, roomid, class_,uuid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_class_pdf);
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
        // Toast.makeText(this, ""+roomid, Toast.LENGTH_SHORT).show();
        Toolbar toolbar = findViewById(R.id.toolbar);

        toolbar.setTitle("Class Pdf");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setElevation(10.0f);
        firebaseFirestore= FirebaseFirestore.getInstance();
        firebaseAuth= FirebaseAuth.getInstance();
        recyclerView=findViewById(R.id.rreeeed);

        getList=new ArrayList<>();
        getDataAdapter1=new getDataAdapter1(getList);
        firebaseFirestore=FirebaseFirestore.getInstance();
        documentReference=firebaseFirestore.collection(roomid).document();

        recyclerView.setLayoutManager(new LinearLayoutManager(MyClassPdf.this));
        recyclerView.setAdapter(getDataAdapter1);
        reciveData();
    }
    private void reciveData() {
        firebaseFirestore.collection(roomid).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                for (DocumentChange ds:queryDocumentSnapshots.getDocumentChanges()) {
                    if (ds.getType()== DocumentChange.Type.ADDED) {
                 /*String first;
                 first = ds.getDocument().getString("name");
                 Toast.makeText(MainActivity2.this, "" + first, Toast.LENGTH_SHORT).show();*/
                        PDF_upload get=ds.getDocument().toObject(PDF_upload.class);
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