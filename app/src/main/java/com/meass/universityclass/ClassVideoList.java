package com.meass.universityclass;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

public class ClassVideoList extends AppCompatActivity {
    FloatingActionButton addvideo;
    RecyclerView recview;
    FirebaseFirestore firebaseFirestore;
    FirebaseAuth firebaseAuth;
    EditText username,usernam2e,username3,username4;
    Button loginnn;
    private String name, section, roomid, class_,uuid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_video_list);
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

        toolbar.setTitle("Class Video");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setElevation(10.0f);
        firebaseFirestore= FirebaseFirestore.getInstance();
        firebaseAuth= FirebaseAuth.getInstance();
        recview=(RecyclerView)findViewById(R.id.rreeeed);
        recview.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<filemodel> options =
                new FirebaseRecyclerOptions.Builder<filemodel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child(roomid), filemodel.class)
                        .build();


        FirebaseRecyclerAdapter<filemodel,myviewholder> firebaseRecyclerAdapter=new FirebaseRecyclerAdapter<filemodel, myviewholder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull myviewholder holder, int position, @NonNull final filemodel model) {
                holder.prepareexoplayer(getApplication(),model.getTitle(),model.getVurl());
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(final View v) {

                    }
                });

            }

            @NonNull
            @Override
            public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.singelrow,parent,false);
                return new myviewholder(view);
            }
        };

        firebaseRecyclerAdapter.startListening();
        recview.setAdapter(firebaseRecyclerAdapter);


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
