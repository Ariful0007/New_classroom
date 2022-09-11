package com.meass.universityclass;

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

public class AnswerAdapter__Exam_2 extends RecyclerView.Adapter<AnswerAdapter__Exam_2.myView> {
    private List<AnswerModel> data;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
String detector;
    private String name, section, roomid, class_,uuid;
    public AnswerAdapter__Exam_2(List<AnswerModel> data, String detector,   String name,String section,String
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
    public AnswerAdapter__Exam_2.myView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.anser, parent, false);
        return new AnswerAdapter__Exam_2.myView(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AnswerAdapter__Exam_2.myView holder, final int position) {
       holder.customer_name.setText("Date : "+data.get(position).getDate()+"\nName : "+data.get(position).getName()+"\n" +
               "\nExam Name : "+data.get(position).getEmail() +
               "\nQuestion : \n\n"+data.get(position).getQuestion()+"\n"+
               "\n\nAnswer : "+data.get(position).getAnswer());

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
