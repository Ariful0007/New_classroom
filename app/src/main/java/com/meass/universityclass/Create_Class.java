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
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.kaopiz.kprogresshud.KProgressHUD;

import java.util.UUID;

import es.dmoral.toasty.Toasty;

public class Create_Class extends AppCompatActivity {
FirebaseFirestore firebaseFirestore;
FirebaseAuth firebaseAuth;
EditText username,usernam2e,username3,username4;
Button loginnn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create__class);
        Toolbar toolbar = findViewById(R.id.toolbar);

        toolbar.setTitle("Create Class");
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
                    AlertDialog.Builder builder=new AlertDialog.Builder(Create_Class.this);
                    builder.setTitle("Confirmation")
                            .setMessage("Are  you want to create this classs?")
                            .setPositiveButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }).setNegativeButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            firebaseFirestore.collection("All_Class_ID")
                                    .document(username3.getText().toString())
                                    .get()
                                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                            if (task.isSuccessful()) {
                                                if (task.getResult().exists()) {
                                                    Toasty.info(getApplicationContext(),"Room ID already Taken",Toasty.LENGTH_SHORT,true).show();
                                                    return;
                                                }
                                                else {
                                                    final KProgressHUD progressDialog = KProgressHUD.create(Create_Class.this)
                                                            .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                                                            .setLabel("Creating Class.....")
                                                            .setCancellable(false)
                                                            .setAnimationSpeed(2)
                                                            .setDimAmount(0.5f)
                                                            .show();
                                                    Long tsLong = System.currentTimeMillis()/1000;
                                                   String ts = tsLong.toString();
                                                   String uuid= UUID.randomUUID().toString();
                                                    Classs_Model classs_model=new Classs_Model(username.getText().toString(),usernam2e.getText().toString()
                                                    ,username3.getText().toString(),username4.getText().toString(),uuid,ts,firebaseAuth.getCurrentUser().getEmail(),firebaseAuth.getCurrentUser().getUid());
                                                    firebaseFirestore.collection("All_Class_ID")
                                                            .document(username3.getText().toString())
                                                            .set(classs_model)
                                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                @Override
                                                                public void onComplete(@NonNull Task<Void> task) {
                                                                    if (task.isSuccessful()) {
                                                                        firebaseFirestore.collection("My_Teach")
                                                                                .document(firebaseAuth.getCurrentUser().getEmail())
                                                                                .collection("List")
                                                                                .document(username3.getText().toString())
                                                                                .set(classs_model)
                                                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                                    @Override
                                                                                    public void onComplete(@NonNull Task<Void> task) {
                                                                                        if (task.isSuccessful()) {
                                                                                            progressDialog.dismiss();
                                                                                            Toasty.success(getApplicationContext(),"Done",Toasty.LENGTH_SHORT,true).show();
                                                                                            startActivity(new Intent(getApplicationContext(),My_Teaching_List.class));
                                                                                        }
                                                                                    }
                                                                                });
                                                                    }
                                                                }
                                                            });
                                                }
                                            }
                                        }
                                    });
                        }
                    }).create().show();
                }
            }
        });


    }
    @Override
    public boolean onSupportNavigateUp() {
        startActivity(new Intent(getApplicationContext(), HomeACTIVITY.class));

        return true;
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(), HomeACTIVITY.class));
    }
}