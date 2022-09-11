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

public class AnswerAdapter extends RecyclerView.Adapter<AnswerAdapter.myView> {
    private List<AnswerModel> data;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
String detector;
    public AnswerAdapter(List<AnswerModel> data, String detector) {

        this.data = data;
        this.detector=detector;
    }

    @NonNull
    @Override
    public AnswerAdapter.myView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.anser, parent, false);
        return new AnswerAdapter.myView(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AnswerAdapter.myView holder, final int position) {
       holder.customer_name.setText("Date : "+data.get(position).getDate()+"\nName : "+data.get(position).getName()+"\n" +
               "\nQuestion : "+data.get(position).getQuestion()+"\n"+
               "Answer : "+data.get(position).getAnswer());

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
