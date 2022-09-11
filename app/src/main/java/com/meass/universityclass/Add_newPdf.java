package com.meass.universityclass;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.kaopiz.kprogresshud.KProgressHUD;

import es.dmoral.toasty.Toasty;

public class Add_newPdf extends AppCompatActivity {

    EditText text_name;
    Button button;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    StorageReference storageReference;
    FirebaseFirestore firebaseFirestore;
    KProgressHUD progressHUD;
    VideoView videoView;
    Button browse,upload;
    Uri videouri;
    EditText vtitle;
    MediaController mediaController;

    FirebaseAuth firebaseAuth;
    EditText username,usernam2e,username3,username4;
    Button loginnn;
    private String name, section, roomid, class_,uuid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_pdf);
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

        toolbar.setTitle("Upload Pdf");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setElevation(10.0f);
        FirebaseApp.initializeApp(Add_newPdf.this);
        firebaseFirestore=FirebaseFirestore.getInstance();
        text_name=findViewById(R.id.text_name);
        button=findViewById(R.id.upload);
        progressHUD = KProgressHUD.create(Add_newPdf.this);
        storageReference= FirebaseStorage.getInstance().getReference();
        databaseReference=FirebaseDatabase.getInstance().getReference("pdf");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PDFTaker();
            }
        });
    }

    private void PDFTaker() {
        Intent intent1=new Intent();
        intent1.setType("application/pdf");
        intent1.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent1,"Choose PDF"),1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==1 && resultCode==RESULT_OK && data!=null && data.getData()!=null) {
            upload_PDF(data.getData());
        }
    }

    private void upload_PDF(Uri data) {
        progress_check();
        StorageReference storageReference1=storageReference.child("PDF/"+System.currentTimeMillis()+".pdf");
        storageReference1.putFile(data)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Task<Uri> uriTask=taskSnapshot.getStorage().getDownloadUrl();
                        while (!uriTask.isComplete());
                        Uri url=uriTask.getResult();
                        PDF_upload pdf_upload=new PDF_upload(text_name.getText().toString().trim(),url.toString());
                        firebaseFirestore.collection(roomid).document(text_name.getText().toString())
                                .set(pdf_upload)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful())
                                        {
                                            progressHUD.dismiss();
                                            Toast.makeText(getApplicationContext(), "Done", Toast.LENGTH_SHORT).show();
                                            text_name.setText(null);
                                            Toast.makeText(getApplicationContext(),"Successfully uploaded",Toast.LENGTH_LONG).show();
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
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressHUD.dismiss();
                Toast.makeText(getApplicationContext(), ""+e.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }
    private void progress_check() {
        progressHUD.setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Please wait")
                .setCancellable(false)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show();
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