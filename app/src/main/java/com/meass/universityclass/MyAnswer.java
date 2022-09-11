package com.meass.universityclass;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.kaopiz.kprogresshud.KProgressHUD;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.UUID;

import es.dmoral.toasty.Toasty;

public class MyAnswer extends AppCompatActivity {
    EditText username,username3,username4;
    Button loginnn;
    TextView usernam2e;
    private String name, section, roomid, class_,uuid,userid;
    FirebaseFirestore firebaseFirestore;
    FirebaseAuth firebaseAuth;

    String name1,section1,class_1,time1,roomid1,uuid1,userid1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_answer);
        try {
            name=getIntent().getStringExtra("name");
            section=getIntent().getStringExtra("section");
            roomid=getIntent().getStringExtra("roomid");
            class_=getIntent().getStringExtra("class_");
            uuid=getIntent().getStringExtra("uuid");

            //
            name1=getIntent().getStringExtra("name1");
            section1=getIntent().getStringExtra("section1");
            roomid1=getIntent().getStringExtra("roomid1");
            class_1=getIntent().getStringExtra("class_1");
            uuid1=getIntent().getStringExtra("uuid1");
            time1=getIntent().getStringExtra("time1");
            userid1=getIntent().getStringExtra("userid1");



        }catch (Exception e) {
            name=getIntent().getStringExtra("name");
            section=getIntent().getStringExtra("section");
            roomid=getIntent().getStringExtra("roomid");
            class_=getIntent().getStringExtra("class_");
            uuid=getIntent().getStringExtra("uuid");

            //
            name1=getIntent().getStringExtra("name1");
            section1=getIntent().getStringExtra("section1");
            roomid1=getIntent().getStringExtra("roomid1");
            class_1=getIntent().getStringExtra("class_1");
            uuid1=getIntent().getStringExtra("uuid1");
            time1=getIntent().getStringExtra("time1");
            userid1=getIntent().getStringExtra("userid1");

        }
        Toolbar toolbar = findViewById(R.id.toolbar);

        toolbar.setTitle("Answer");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setElevation(10.0f);
        firebaseFirestore=FirebaseFirestore.getInstance();
        firebaseAuth=FirebaseAuth.getInstance();
        username=findViewById(R.id.username);
        usernam2e=findViewById(R.id.usernam2e);
        username3=findViewById(R.id.username3);
        username4=findViewById(R.id.username4);
        username.setText(name1);
        usernam2e.setText(section1);
        username3.setText(roomid1);

        loginnn=findViewById(R.id.loginnn);
        loginnn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(username.getText().toString())||TextUtils.isEmpty(usernam2e.getText().toString())||
                        TextUtils.isEmpty(username3.getText().toString())||TextUtils.isEmpty(username4.getText().toString())) {
                    Toasty.error(getApplicationContext(),"Give all inforemation",Toasty.LENGTH_SHORT,true).show();
                    return;
                }
                else {
                    AlertDialog.Builder builder=new AlertDialog.Builder(MyAnswer.this);
                    builder.setTitle("Confirmation")
                            .setMessage("Are you want to upload this answer?")
                            .setPositiveButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                   dialog.dismiss();
                                }
                            }).setNegativeButton("yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            final KProgressHUD progressDialog = KProgressHUD.create(MyAnswer.this)
                                    .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                                    .setLabel("Uploading Data.....")
                                    .setCancellable(false)
                                    .setAnimationSpeed(2)
                                    .setDimAmount(0.5f)
                                    .show();
                            firebaseFirestore.collection("User2")
                                    .document(firebaseAuth.getCurrentUser().getEmail())
                                    .get()
                                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                            if (task.isSuccessful()) {
                                                if (task.getResult().exists()) {
                                                    try {
                                                        String name11111=task.getResult().getString("name");
                                                        Long tsLong = System.currentTimeMillis()/1000;
                                                       String ts = tsLong.toString();
                                                        String uuid= UUID.randomUUID().toString();
                                                        Calendar calendar = Calendar.getInstance();
                                                        String current = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());
                                                        String current1 = DateFormat.getDateInstance().format(calendar.getTime());
                                                        AnswerModel answerModel=new AnswerModel(name11111,username4.getText().toString(),
                                                               ts,current1,firebaseAuth.getCurrentUser().getEmail(),
                                                                username.getText().toString());
                                                        firebaseFirestore.collection("Answer").document("List").collection(roomid)
                                                                .document(uuid)
                                                                .set(answerModel)
                                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                    @Override
                                                                    public void onComplete(@NonNull Task<Void> task) {
                                                                        if (task.isSuccessful()) {
                                                                            firebaseFirestore.collection("MyAnswer")
                                                                                    .document(firebaseAuth.getCurrentUser().getEmail())
                                                                                    .collection(roomid)
                                                                                    .document(ts)
                                                                                    .set(answerModel)
                                                                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                                        @Override
                                                                                        public void onComplete(@NonNull Task<Void> task) {

                                                                                        }
                                                                                    });

                                                                            firebaseFirestore.collection("MyAnswer")
                                                                                    .document(firebaseAuth.getCurrentUser().getEmail())
                                                                                    .collection("List")
                                                                                    .document(time1)
                                                                                    .set(answerModel)
                                                                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                                        @Override
                                                                                        public void onComplete(@NonNull Task<Void> task) {
                                                                                           if (task.isSuccessful()) {
                                                                                               progressDialog.dismiss();
                                                                                               Toasty.success(getApplicationContext(),"Successfully Upload answer",Toasty.LENGTH_SHORT,true).show();
                                                                                               Intent intent=new Intent(getApplicationContext(),MyJoiningHome.class);
                                                                                               intent.putExtra("name",name);
                                                                                               intent.putExtra("section",section);
                                                                                               intent.putExtra("roomid",roomid);
                                                                                               intent.putExtra("class_",class_);
                                                                                               intent.putExtra("uuid",uuid);
                                                                                               startActivity(intent);
                                                                                           }
                                                                                        }
                                                                                    });
                                                                        }
                                                                    }
                                                                });
                                                    }catch (Exception e) {
                                                        String name11111=task.getResult().getString("name");
                                                        Long tsLong = System.currentTimeMillis()/1000;
                                                        String ts = tsLong.toString();
                                                        String uuid= UUID.randomUUID().toString();
                                                        Calendar calendar = Calendar.getInstance();
                                                        String current = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());
                                                        String current1 = DateFormat.getDateInstance().format(calendar.getTime());
                                                        AnswerModel answerModel=new AnswerModel(name11111,username4.getText().toString(),
                                                                ts,current1,firebaseAuth.getCurrentUser().getEmail(),
                                                                username.getText().toString());
                                                        firebaseFirestore.collection("Answer").document("List").collection(roomid)
                                                                .document(uuid)
                                                                .set(answerModel)
                                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                    @Override
                                                                    public void onComplete(@NonNull Task<Void> task) {
                                                                        if (task.isSuccessful()) {
                                                                            firebaseFirestore.collection("MyAnswer")
                                                                                    .document(firebaseAuth.getCurrentUser().getEmail())
                                                                                    .collection(roomid)
                                                                                    .document(ts)
                                                                                    .set(answerModel)
                                                                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                                        @Override
                                                                                        public void onComplete(@NonNull Task<Void> task) {

                                                                                        }
                                                                                    });
                                                                            firebaseFirestore.collection("MyAnswer")
                                                                                    .document(firebaseAuth.getCurrentUser().getEmail())
                                                                                    .collection("List")
                                                                                    .document(time1)
                                                                                    .set(answerModel)
                                                                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                                        @Override
                                                                                        public void onComplete(@NonNull Task<Void> task) {
                                                                                            if (task.isSuccessful()) {
                                                                                                progressDialog.dismiss();
                                                                                                Toasty.success(getApplicationContext(),"Successfully Upload answer",Toasty.LENGTH_SHORT,true).show();
                                                                                                Intent intent=new Intent(getApplicationContext(),MyJoiningHome.class);
                                                                                                intent.putExtra("name",name);
                                                                                                intent.putExtra("section",section);
                                                                                                intent.putExtra("roomid",roomid);
                                                                                                intent.putExtra("class_",class_);
                                                                                                intent.putExtra("uuid",uuid);
                                                                                                startActivity(intent);
                                                                                            }
                                                                                        }
                                                                                    });
                                                                        }
                                                                    }
                                                                });
                                                    }
                                                }
                                            }
                                        }
                                    });

                        }
                    }).create();
                    builder.show();

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