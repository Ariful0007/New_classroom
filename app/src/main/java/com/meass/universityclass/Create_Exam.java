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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.kaopiz.kprogresshud.KProgressHUD;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.UUID;

import es.dmoral.toasty.Toasty;

public class Create_Exam extends AppCompatActivity {
    FirebaseFirestore firebaseFirestore;
    FirebaseAuth firebaseAuth;
    EditText username,usernam2e,username3,username4;
    Button loginnn;
    private String name, section, roomid, class_,uuid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create__exam);
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

        toolbar.setTitle("Create Assignment");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setElevation(10.0f);
        firebaseFirestore=FirebaseFirestore.getInstance();
        firebaseAuth=FirebaseAuth.getInstance();
        username=findViewById(R.id.username);
        usernam2e=findViewById(R.id.usernam2e);
        username3=findViewById(R.id.username3);
        username4=findViewById(R.id.username4);

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
                    AlertDialog.Builder builder=new AlertDialog.Builder(Create_Exam.this);
                    builder.setTitle("Confirmation")
                            .setMessage("Are  you want to create this Exam?")
                            .setPositiveButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }).setNegativeButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            final KProgressHUD progressDialog = KProgressHUD.create(Create_Exam.this)
                                    .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                                    .setLabel("Creating Exam.....")
                                    .setCancellable(false)
                                    .setAnimationSpeed(2)
                                    .setDimAmount(0.5f)
                                    .show();
                            Long tsLong = System.currentTimeMillis()/1000;
                            String ts = tsLong.toString();
                            String uuid= UUID.randomUUID().toString();
                            Calendar calendar = Calendar.getInstance();
                            String current = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());
                            String current1 = DateFormat.getDateInstance().format(calendar.getTime());
                            Classs_Model classs_model=new Classs_Model(username.getText().toString(),usernam2e.getText().toString()
                                    ,username3.getText().toString(),username4.getText().toString(),uuid,ts,roomid,current1);

                            firebaseFirestore.collection("Exam")
                                    .document("list").
                        collection(roomid)
                                    .document(ts)
                                    .set(classs_model)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                progressDialog.dismiss();
                                                Toasty.success(getApplicationContext(),"Successfully Adding",Toasty.LENGTH_SHORT,true).show();

                                                //name, section, roomid, class_,uuid
                                                Intent intent=new Intent(getApplicationContext(),MyClassHome.class);
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
                    }).create();
                    builder.show();
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