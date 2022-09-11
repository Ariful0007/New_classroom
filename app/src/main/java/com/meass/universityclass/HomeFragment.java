package com.meass.universityclass;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.InputType;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.kaopiz.kprogresshud.KProgressHUD;

import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;


public class HomeFragment extends Fragment {

 View view;
 FloatingActionButton fab_plus;
 FirebaseFirestore firebaseFirestore;
 FirebaseAuth firebaseAuth;

 ////////
 LottieAnimationView empty_cart;
    DocumentReference documentReference;
    RecyclerView recyclerView;
    ClassAdapter getDataAdapter1;
    List<Classs_Model> getList;
    String url;
    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_home, container, false);
firebaseAuth=FirebaseAuth.getInstance();
firebaseFirestore=FirebaseFirestore.getInstance();
        // Setting dialogview
        fab_plus=view.findViewById(R.id.fab_plus);
        fab_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String list[] = {"Create Class"};
                AlertDialog.Builder builder=new AlertDialog.Builder(view.getContext());
                builder.setTitle("Options")
                        .setItems(list, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (which==0) {
                                    view.getContext().startActivity(new Intent(view.getContext(),Create_Class.class));
                                }
                                else if(which==1) {
                                    final EditText input = new EditText(view.getContext());
                                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                                            LinearLayout.LayoutParams.MATCH_PARENT,
                                            LinearLayout.LayoutParams.MATCH_PARENT);
                                    input.setLayoutParams(lp);
                                    input.setGravity(Gravity.CENTER);
                                    input.setHint("Enter Room ID");
                                    input.setInputType(InputType.TYPE_CLASS_PHONE);

                                    new androidx.appcompat.app.AlertDialog.Builder(view.getContext())
                                            .setTitle("Join Class")
                                            .setMessage("If you want to join class. Please enter class room ID.\n")
                                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialogInterface, int i) {

                                                    if (TextUtils.isEmpty(input.getText().toString()))
                                                    {
                                                        Toast.makeText(getContext(), "Enter Room ID", Toast.LENGTH_SHORT).show();
                                                    }
                                                    else {
                                                        final KProgressHUD progressDialog = KProgressHUD.create(view.getContext())
                                                                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                                                                .setLabel("Checking Data.....")
                                                                .setCancellable(false)
                                                                .setAnimationSpeed(2)
                                                                .setDimAmount(0.5f)
                                                                .show();
                                                        firebaseFirestore.collection("All_Class_ID")
                                                                .document(input.getText().toString())
                                                                .get()
                                                                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                                    @Override
                                                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                                        if (task.isSuccessful()) {
                                                                            if (task.getResult().exists()) {
                                                                                String class_=task.getResult().getString("class_");
                                                                                String email=task.getResult().getString("email");
                                                                               String name=task.getResult().getString("name");

                                                                                String roomid=task.getResult().getString("roomid");
                                                                                String section=task.getResult().getString("section");
                                                                                String time=task.getResult().getString("time");

                                                                                String userid=task.getResult().getString("userid");
                                                                                String uuid=task.getResult().getString("uuid");
                                                                                firebaseFirestore.collection("User2")
                                                                                        .document(firebaseAuth.getCurrentUser().getEmail())
                                                                                        .get()
                                                                                        .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                                                            @Override
                                                                                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                                                                if (task.isSuccessful()) {
                                                                                                    Long tsLong = System.currentTimeMillis()/1000;
                                                                                                    String ts = tsLong.toString();
                                                                                                    String name=task.getResult().getString("name");
                                                                                                    String image=task.getResult().getString("image");
                                                                                                    Class_people class_people=new Class_people(name,image,"12",ts,firebaseAuth.getCurrentUser().getEmail());
                                                                                                    firebaseFirestore.collection("PeopleList")
                                                                                                            .document(roomid)
                                                                                                            .collection("List")
                                                                                                            .document(firebaseAuth.getCurrentUser().getEmail())
                                                                                                            .set(class_people)
                                                                                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                                                                @Override
                                                                                                                public void onComplete(@NonNull Task<Void> task) {

                                                                                                                }
                                                                                                            });
                                                                                                }
                                                                                            }
                                                                                        });
                                                                                Classs_Model classs_model=new Classs_Model(name,section,roomid,class_,uuid,time,email,userid);
                                                                                firebaseFirestore.collection("My_Class")
                                                                                        .document(firebaseAuth.getCurrentUser().getEmail())
                                                                                        .collection("List")
                                                                                        .document(roomid)
                                                                                        .set(classs_model)
                                                                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                                            @Override
                                                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                                                if (task.isSuccessful()) {
                                                                                                    progressDialog.dismiss();
                                                                                                    Toasty.success(getContext(),"Successfully join this class",Toasty.LENGTH_SHORT,true).show();
                                                                                                    dialog.dismiss();
                                                                                                }
                                                                                            }
                                                                                        });



                                                                            }
                                                                            else {
                                                                                progressDialog.dismiss();
                                                                                Toasty.warning(getContext(),"Invalid class ID",Toasty.LENGTH_SHORT,true).show();
                                                                            }
                                                                        }
                                                                    }
                                                                });

                                                    }
                                                }
                                            }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            dialogInterface.dismiss();
                                        }
                                    }). setIcon(R.drawable.classroom)
                                            .setView(input)
                                            .show();
                                }
                            }
                        }).create().show();
            }
        });


        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();


        getList = new ArrayList<>();
        getDataAdapter1 = new ClassAdapter(getList,"as");
        firebaseFirestore = FirebaseFirestore.getInstance();
        documentReference =      firebaseFirestore.collection("My_Teach")
                .document(firebaseAuth.getCurrentUser().getEmail())
                .collection("List")
                .document();
        recyclerView = view.findViewById(R.id.blog_list_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setAdapter(getDataAdapter1);
        reciveData();
        return view;
    }
    private void reciveData() {

        firebaseFirestore.collection("My_Teach")
                .document(firebaseAuth.getCurrentUser().getEmail())
                .collection("List")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                        for (DocumentChange ds : queryDocumentSnapshots.getDocumentChanges()) {
                            if (ds.getType() == DocumentChange.Type.ADDED) {

                 /*String first;
                 first = ds.getDocument().getString("name");
                 Toast.makeText(MainActivity2.this, "" + first, Toast.LENGTH_SHORT).show();*/
                                Classs_Model get = ds.getDocument().toObject(Classs_Model.class);
                                getList.add(get);
                                getDataAdapter1.notifyDataSetChanged();
                            }

                        }
                    }
                });

    }
}