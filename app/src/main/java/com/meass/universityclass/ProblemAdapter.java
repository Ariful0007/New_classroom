package com.meass.universityclass;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;
import java.util.Random;

public class ProblemAdapter extends RecyclerView.Adapter<ProblemAdapter.myView> {
    private List<Classs_Model> data;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
String detector;
    public ProblemAdapter(List<Classs_Model> data, String detector) {

        this.data = data;
        this.detector=detector;
    }

    @NonNull
    @Override
    public ProblemAdapter.myView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.anser, parent, false);
        return new ProblemAdapter.myView(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProblemAdapter.myView holder, final int position) {
       holder.customer_name.setText("Code : \n"+data.get(position).getSection()+"\n\nProblem : "+data.get(position).getName());
holder.customer_name.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent intent=new Intent(v.getContext(),DetailsView.class);
        intent.putExtra("time",data.get(position).getTime());
        intent.putExtra("code",data.get(position).getSection());
        intent.putExtra("problem",data.get(position).getName());
        v.getContext().startActivity(intent);
    }
});

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class myView extends RecyclerView.ViewHolder {
        TextView customer_name,blog_detail_desc,packagetime;
        RelativeLayout card_view8;
        Button bloas;
        ImageView imafes;
        public myView(@NonNull View itemView) {

            super(itemView);

            customer_name=itemView.findViewById(R.id.customer_name);
            card_view8=itemView.findViewById(R.id.card_view8);
        }
    }
}
