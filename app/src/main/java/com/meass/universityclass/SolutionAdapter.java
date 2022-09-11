package com.meass.universityclass;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;
import java.util.Random;

import static android.content.Context.CLIPBOARD_SERVICE;

public class SolutionAdapter extends RecyclerView.Adapter<SolutionAdapter.myView> {
    private List<AnswerModel> data;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
String detector;
    public SolutionAdapter(List<AnswerModel> data, String detector) {

        this.data = data;
        this.detector=detector;
    }

    @NonNull
    @Override
    public SolutionAdapter.myView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.solution, parent, false);
        return new SolutionAdapter.myView(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SolutionAdapter.myView holder, final int position) {
        myClipboard = (ClipboardManager)holder.itemView.getContext().getSystemService(CLIPBOARD_SERVICE);
       holder.customer_name.setText("Solved By : "+data.get(position).getName()+"\nDate : "+data.get(position).getDate()+"\n" +
               "Solution : \n\n"+data.get(position).getAnswer());
       holder.customer_name.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               String text = data.get(position).getAnswer();
               myClip = ClipData.newPlainText("text", text);
               myClipboard.setPrimaryClip(myClip);
               Toast.makeText(v.getContext(), "Answer Copied",
                       Toast.LENGTH_SHORT).show();
           }
       });

    }
    private ClipboardManager myClipboard;
    private ClipData myClip;
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
