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

public class JoinAdapter extends RecyclerView.Adapter<JoinAdapter.myView> {
    private List<Classs_Model> data;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
String detector;
    public JoinAdapter(List<Classs_Model> data, String detector) {

        this.data = data;
        this.detector=detector;
    }

    @NonNull
    @Override
    public JoinAdapter.myView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.healthcard, parent, false);
        return new JoinAdapter.myView(view);
    }

    @Override
    public void onBindViewHolder(@NonNull JoinAdapter.myView holder, final int position) {
        holder.packagename.setText(data.get(position).getName());
        holder.packagetime.setText(data.get(position).getSection());
        holder.bloas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(v.getContext(),MyJoiningHome.class);
                intent.putExtra("name",data.get(position).getName());
                intent.putExtra("section",data.get(position).getSection());
                intent.putExtra("roomid",data.get(position).getRoomid());
                intent.putExtra("class_",data.get(position).getClass_());
                intent.putExtra("uuid",data.get(position).getUuid());
                intent.putExtra("time",data.get(position).getTime());
                v.getContext().startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class myView extends RecyclerView.ViewHolder {
        TextView packagename,blog_detail_desc,packagetime;
        CardView cardsddd;
        Button bloas;
        ImageView imafes;
        public myView(@NonNull View itemView) {

            super(itemView);
            packagename=itemView.findViewById(R.id.packagename);
            blog_detail_desc=itemView.findViewById(R.id.blog_detail_desc);
            bloas=itemView.findViewById(R.id.bloas);
            imafes=itemView.findViewById(R.id.imafes);
            packagetime=itemView.findViewById(R.id.packagetime);


        }
    }
}
