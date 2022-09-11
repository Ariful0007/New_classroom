package com.meass.universityclass;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

import es.dmoral.toasty.Toasty;

public class DetailsView extends AppCompatActivity {
String time,code,problem;
TextView customer_name;
RecyclerView commentRecyclerView;
EditText comment;
ImageButton comment_send;
FirebaseFirestore firebaseFirestore;
FirebaseAuth firebaseAuth;
//
LottieAnimationView empty_cart;
    DocumentReference documentReference;
    RecyclerView recyclerView;
    SolutionAdapter getDataAdapter1;
    List<AnswerModel> getList;
    String url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_view);
        Toolbar toolbar = findViewById(R.id.toolbar);

        toolbar.setTitle("Comments");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setElevation(10.0f);
        try {
            time=getIntent().getStringExtra("time");
            code=getIntent().getStringExtra("code");
            problem=getIntent().getStringExtra("problem");

        }catch (Exception e) {
            time=getIntent().getStringExtra("time");
            code=getIntent().getStringExtra("code");
            problem=getIntent().getStringExtra("problem");
        }
        customer_name=findViewById(R.id.customer_name);
        customer_name.setText("Code : \n\n"+code+"\n\nProblem : "+problem);
        commentRecyclerView=findViewById(R.id.commentRecyclerView);
        comment=findViewById(R.id.comment);
        comment_send=findViewById(R.id.comment_send);
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseFirestore=FirebaseFirestore.getInstance();
        comment_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(comment.getText().toString())) {
                    Toasty.error(getApplicationContext(),"Enter answer",Toasty.LENGTH_SHORT,true).show();
                    return;
                }
                else {
                    firebaseFirestore.collection("Users")
                            .document(firebaseAuth.getCurrentUser().getUid())
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    if (task.isSuccessful()) {
                                        if (task.getResult().exists()) {
                                            String name=task.getResult().getString("name");
                                            String image=task.getResult().getString("image");
                                            Long tsLong = System.currentTimeMillis()/1000;
                                            String ts = tsLong.toString();
                                            String uuid= UUID.randomUUID().toString();
                                            Calendar calendar = Calendar.getInstance();
                                            String current = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());
                                            String current1 = DateFormat.getDateInstance().format(calendar.getTime());
                                            AnswerModel answerModel=new AnswerModel(name,comment.getText().toString(),
                                                   ts,current1,firebaseAuth.getCurrentUser().getEmail(),problem );
                                            firebaseFirestore.collection("Comments")
                                                    .document(time)
                                                    .collection("List")
                                                    .document(ts)
                                                    .set(answerModel)
                                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {
                                                            if (task.isSuccessful()) {
                                                                Toast.makeText(DetailsView.this, "Send", Toast.LENGTH_SHORT).show();
                                                            }
                                                        }
                                                    });
                                        }
                                    }
                                }
                            });
                }
            }
        });
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();


        getList = new ArrayList<>();
        getDataAdapter1 = new SolutionAdapter(getList,"as");
        firebaseFirestore = FirebaseFirestore.getInstance();
        documentReference =      firebaseFirestore.collection("Comments")
                .document(time)
                .collection("List")
                .document();
        recyclerView = findViewById(R.id.commentRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(DetailsView.this));
        recyclerView.setAdapter(getDataAdapter1);
        reciveData();


    }
    private void reciveData() {

        firebaseFirestore.collection("Comments")
                .document(time)
                .collection("List")
                .orderBy("time", Query.Direction.DESCENDING)
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
        startActivity(new Intent(getApplicationContext(), Coding_Home.class));

        return true;
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(), Coding_Home.class));
    }
}