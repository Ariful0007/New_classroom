package com.meass.universityclass;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

import es.dmoral.toasty.Toasty;

public class MyAssignmentAdapter extends RecyclerView.Adapter<MyAssignmentAdapter.myView> {
    private List<Classs_Model> data;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
String detector;
    private String name, section, roomid, class_,uuid;
    public MyAssignmentAdapter(List<Classs_Model> data, String detector,String name,String section,String roomid,String class_,String uuid) {
this.name=name;
this.section=section;
this.roomid=roomid;
this.class_=class_;
this.uuid=uuid;
        this.data = data;
        this.detector=detector;
    }

    @NonNull
    @Override
    public MyAssignmentAdapter.myView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.asssignemnt, parent, false);
        return new MyAssignmentAdapter.myView(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAssignmentAdapter.myView holder, final int position) {
       holder.customer_name.setText("New Assignemnt : "+data.get(position).getName()+"\n" +
               "Date : "+data.get(position).getUserid());
       firebaseAuth=FirebaseAuth.getInstance();
       firebaseFirestore =FirebaseFirestore.getInstance();
        //Toast.makeText(holder.itemView.getContext(), "0", Toast.LENGTH_SHORT).show();
        holder.classs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseFirestore.collection("MyAnswer")
                        .document(firebaseAuth.getCurrentUser().getEmail())
                        .collection("List")
                        .document(data.get(position).getTime())
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if (task.isSuccessful()) {
                                    if (task.getResult().exists()) {
                                        Toasty.warning(v.getContext(),"Already Submitted answer",Toasty.LENGTH_SHORT,true).show();
                                        return;
                                    }
                                    else {
                                        Intent intent=new Intent(v.getContext(),MyAnswer.class);
                                        intent.putExtra("name1",data.get(position).getName());
                                        intent.putExtra("section1",data.get(position).getSection());
                                        intent.putExtra("roomid1",data.get(position).getRoomid());
                                        intent.putExtra("class_1",data.get(position).getClass_());
                                        intent.putExtra("uuid1",data.get(position).getUuid());
                                        intent.putExtra("time1",data.get(position).getTime());
                                        intent.putExtra("userid1",data.get(position).getUserid());

                                        //
                                        intent.putExtra("name",name);
                                        intent.putExtra("section",section);
                                        intent.putExtra("roomid",roomid);
                                        intent.putExtra("class_",class_);
                                        intent.putExtra("uuid",uuid);

                                        v.getContext().startActivity(intent);
                                    }
                                }
                            }
                        });

            }
        });
        holder.cardsddd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseFirestore.collection("MyAnswer")
                        .document(firebaseAuth.getCurrentUser().getEmail())
                        .collection("List")
                        .document(data.get(position).getTime())
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if (task.isSuccessful()) {
                                    if (task.getResult().exists()) {
                                        Toasty.warning(v.getContext(),"Already Submitted answer",Toasty.LENGTH_SHORT,true).show();
                                        return;
                                    }
                                    else {
                                        Intent intent=new Intent(v.getContext(),MyAnswer.class);
                                        intent.putExtra("name1",data.get(position).getName());
                                        intent.putExtra("section1",data.get(position).getSection());
                                        intent.putExtra("roomid1",data.get(position).getRoomid());
                                        intent.putExtra("class_1",data.get(position).getClass_());
                                        intent.putExtra("uuid1",data.get(position).getUuid());
                                        intent.putExtra("time1",data.get(position).getTime());
                                        intent.putExtra("userid1",data.get(position).getUserid());

                                        //
                                        intent.putExtra("name",name);
                                        intent.putExtra("section",section);
                                        intent.putExtra("roomid",roomid);
                                        intent.putExtra("class_",class_);
                                        intent.putExtra("uuid",uuid);

                                        v.getContext().startActivity(intent);
                                    }
                                }
                            }
                        });
            }
        });

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class myView extends RecyclerView.ViewHolder {
        TextView customer_name,blog_detail_desc,packagetime;
        CardView cardsddd;
        Button bloas;
        ImageView imafes;
        TextView classs;
        public myView(@NonNull View itemView) {

            super(itemView);

            customer_name=itemView.findViewById(R.id.customer_name);
            classs=itemView.findViewById(R.id.classs);
            cardsddd=itemView.findViewById(R.id.card_view8);
        }
    }
}
