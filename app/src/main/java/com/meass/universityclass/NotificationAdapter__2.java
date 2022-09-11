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

public class NotificationAdapter__2 extends RecyclerView.Adapter<NotificationAdapter__2.myView> {
    private List<Classs_Model> data;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
String detector;
    public NotificationAdapter__2(List<Classs_Model> data, String detector) {

        this.data = data;
        this.detector=detector;
    }

    @NonNull
    @Override
    public NotificationAdapter__2.myView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.anser, parent, false);
        return new NotificationAdapter__2.myView(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationAdapter__2.myView holder, final int position) {
        holder.customer_name.setText("Notification Title : "+data.get(position).getName()+"\n\n Notification Details : "+data.get(position).getSection());

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
