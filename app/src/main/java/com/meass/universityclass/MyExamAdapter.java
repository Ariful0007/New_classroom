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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class MyExamAdapter extends RecyclerView.Adapter<MyExamAdapter.myView> {
    private List<Classs_Model> data;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
String detector;
    private String name, section, roomid, class_,uuid;
    public MyExamAdapter(List<Classs_Model> data, String detector,   String name,String section,String
            roomid, String class_,String uuid) {
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
    public MyExamAdapter.myView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.anser, parent, false);
        return new MyExamAdapter.myView(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyExamAdapter.myView holder, final int position) {
       holder.customer_name.setText("Exam Name : "+data.get(position).getName()+"\n" +
               "Date and Time : "+data.get(position).getRoomid()+"\n Total Mark : "+data.get(position).getSection()+"\n\n" +
               "Question : \n\n"+data.get(position).getClass_());
       holder.customer_name.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intentclas=new Intent(v.getContext(),ExaminandAnsweList.class);
               intentclas.putExtra("name",name);
               intentclas.putExtra("section",section);
               intentclas.putExtra("roomid",roomid);
               intentclas.putExtra("class_",class_);
               intentclas.putExtra("uuid",uuid);
               intentclas.putExtra("time",data.get(position).getTime());
               v.getContext().startActivity(intentclas);
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
        public myView(@NonNull View itemView) {

            super(itemView);

            customer_name=itemView.findViewById(R.id.customer_name);

        }
    }
}
