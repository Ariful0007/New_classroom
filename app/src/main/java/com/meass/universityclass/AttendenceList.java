package com.meass.universityclass;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

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
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import es.dmoral.toasty.Toasty;

public class AttendenceList extends AppCompatActivity {
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
    private ClipboardManager myClipboard;
    private ClipData myClip;
Button upload;
    Calendar calendar = Calendar.getInstance();
    String current = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());
    String current1 = DateFormat.getDateInstance().format(calendar.getTime());
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendence_list);
        FirebaseApp.initializeApp(AttendenceList.this);
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

        toolbar.setTitle("Today's Attendence");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setElevation(10.0f);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        myClipboard = (ClipboardManager)getSystemService(CLIPBOARD_SERVICE);

        getList = new ArrayList<>();
        getDataAdapter1 = new AttendenceAdapter(getList,"as");
        firebaseFirestore = FirebaseFirestore.getInstance();

        documentReference =      firebaseFirestore.collection(roomid).document(current1).collection("List")

                .document();
        recyclerView = findViewById(R.id.rreeeed);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(AttendenceList.this));
        recyclerView.setAdapter(getDataAdapter1);
        reciveData();
        upload=findViewById(R.id.upload);
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseFirestore.collection(roomid).document(current1).collection("List").get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                int ncount=0;
                                for (DocumentSnapshot document : task.getResult()) {
                                    ncount++;
                                }
                                if (ncount==0) {
                                    Toasty.info(getApplicationContext(),"No Student attendence found.",Toasty.LENGTH_SHORT,true).show();

                                }
                                else {
                                    int n=1;
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        String latitude2 = document.getString("name");
                                        String longitude2 = document.getString("email");
                                        text=text+"\nStudent No : "+n+"\nStudent Name : "+latitude2+"\n" +
                                                "Class Joining Time : "+longitude2;

                                    }
                                    Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                                            "mailto","abc@mail.com", null));
                                    emailIntent.putExtra(Intent.EXTRA_EMAIL, ""+text);
                                    emailIntent.putExtra(Intent.EXTRA_SUBJECT, ""+text);
                                    emailIntent.putExtra(Intent.EXTRA_TEXT, ""+text);
                                    startActivity(Intent.createChooser(emailIntent, "Send Email..."));
                                    AlertDialog.Builder builder=new AlertDialog.Builder(AttendenceList.this);
                                    builder.setTitle("Copy it")
                                            .setMessage("Are you want to copy it?")
                                            .setPositiveButton("Copy it", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    dialog.dismiss();
                                                    String text1 = text;
                                                    myClip = ClipData.newPlainText("text", text1);
                                                    myClipboard.setPrimaryClip(myClip);
                                                    Toast.makeText(getApplicationContext(), " Copied",
                                                            Toast.LENGTH_SHORT).show();
                                                    Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                                                            "mailto","abc@mail.com", null));
                                                    emailIntent.putExtra(Intent.EXTRA_EMAIL, ""+text);
                                                    emailIntent.putExtra(Intent.EXTRA_SUBJECT, ""+text);
                                                    emailIntent.putExtra(Intent.EXTRA_TEXT, ""+text);
                                                    startActivity(Intent.createChooser(emailIntent, "Send Email..."));
                                                }
                                            }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();

                                        }
                                    });
                                    builder.create();
                                    builder.show();

                                }
                            }
                        });
            }
        });

    }
    String text="";
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