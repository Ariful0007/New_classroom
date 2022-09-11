package com.meass.universityclass;

import android.content.DialogInterface;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.kaopiz.kprogresshud.KProgressHUD;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

import es.dmoral.toasty.Toasty;

public class MyExamAdapter__2 extends RecyclerView.Adapter<MyExamAdapter__2.myView> {
    private List<Classs_Model> data;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
String detector;
    public MyExamAdapter__2(List<Classs_Model> data, String detector) {

        this.data = data;
        this.detector=detector;
    }

    @NonNull
    @Override
    public MyExamAdapter__2.myView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.anser, parent, false);
        return new MyExamAdapter__2.myView(view);

    }

    @Override
    public void onBindViewHolder(@NonNull MyExamAdapter__2.myView holder, final int position) {
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseFirestore=FirebaseFirestore.getInstance();
       holder.customer_name.setText("Exam Name : "+data.get(position).getName()+"\n" +
               "Date and Time : "+data.get(position).getRoomid()+"\n Total Mark : "+data.get(position).getSection()+"\n\n" +
               "Question : \n\n"+data.get(position).getClass_());
       holder.customer_name.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               firebaseFirestore.collection("My_ExamAnswer")
                       .document(firebaseAuth.getCurrentUser().getEmail())
                       .collection("List")
                       .document(data.get(position).getTime())
                       .get()
                       .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                           @Override
                           public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                               if (task.isSuccessful()) {
                                   if (task.getResult().exists()) {
                                       Toasty.error(v.getContext(),"You are already taken this exam",Toasty.LENGTH_SHORT,true).show();
                                       return;
                                   }
                                   else {
                                       final EditText input = new EditText(v.getContext());
                                       LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                                               LinearLayout.LayoutParams.MATCH_PARENT,
                                               LinearLayout.LayoutParams.MATCH_PARENT);
                                       input.setLayoutParams(lp);
                                       input.setGravity(Gravity.CENTER);
                                       input.setHint("Enter all answer");

                                       new AlertDialog.Builder(v.getContext())
                                               .setTitle("Answer Section")
                                               .setMessage("Enter all answer of this exam question.")
                                               .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                                   @Override
                                                   public void onClick(DialogInterface dialogInterface, int i) {

                                                       if (TextUtils.isEmpty(input.getText().toString()))
                                                       {
                                                           Toast.makeText(v.getContext(), "Enter Values", Toast.LENGTH_SHORT).show();
                                                       }
                                                       else {
                                                           final KProgressHUD progressDialog = KProgressHUD.create(v.getContext())
                                                                   .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                                                                   .setLabel("Uploading Data.....")
                                                                   .setCancellable(false)
                                                                   .setAnimationSpeed(2)
                                                                   .setDimAmount(0.5f)
                                                                   .show();
                                                           firebaseFirestore.collection("User2")
                                                                   .document(firebaseAuth.getCurrentUser().getEmail())
                                                                   .get()
                                                                   .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                                       @Override
                                                                       public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                                           if (task.isSuccessful()) {
                                                                               if (task.getResult().exists()) {
                                                                                   String name=task.getResult().getString("name");
                                                                                   Long tsLong = System.currentTimeMillis()/1000;
                                                                                   String ts = tsLong.toString();
                                                                                   String uuid= UUID.randomUUID().toString();
                                                                                   Calendar calendar = Calendar.getInstance();
                                                                                   String current = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());
                                                                                   String current1 = DateFormat.getDateInstance().format(calendar.getTime());
                                                                                   AnswerModel answerModel =new AnswerModel(name,input.getText().toString(),data.get(position).getTime(),
                                                                                           current1,data.get(position).getName(),data.get(position).getClass_());
                                                                                   firebaseFirestore.collection("My_ExamAnswer")
                                                                                           .document(firebaseAuth.getCurrentUser().getEmail())
                                                                                           .collection("List")
                                                                                           .document(data.get(position).getTime())
                                                                                           .set(answerModel)
                                                                                           .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                                               @Override
                                                                                               public void onComplete(@NonNull Task<Void> task) {

                                                                                               }
                                                                                           });
                                                                                   firebaseFirestore.collection("My_ExamAnswer")
                                                                                           .document(firebaseAuth.getCurrentUser().getEmail())
                                                                                           .collection(data.get(position).getEmail())
                                                                                           .document(data.get(position).getTime())
                                                                                           .set(answerModel)
                                                                                           .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                                               @Override
                                                                                               public void onComplete(@NonNull Task<Void> task) {
                                                                                                   if (task.isSuccessful()) {
                                                                                                       firebaseFirestore.collection("All_Answers")
                                                                                                               .document("List")
                                                                                                               .collection(data.get(position).getTime())
                                                                                                               .document(uuid)
                                                                                                               .set(answerModel)
                                                                                                               .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                                                                   @Override
                                                                                                                   public void onComplete(@NonNull Task<Void> task) {
                                                                                                                       if (task.isSuccessful()) {
                                                                                                                           if (task.isSuccessful()) {
                                                                                                                               progressDialog.dismiss();
                                                                                                                               Toasty.success(v.getContext(),"Successfully Given This Exam.",Toasty.LENGTH_SHORT,true).show();

                                                                                                                           }
                                                                                                                       }
                                                                                                                   }
                                                                                                               });
                                                                                                   }

                                                                                               }
                                                                                           });
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
        public myView(@NonNull View itemView) {

            super(itemView);

            customer_name=itemView.findViewById(R.id.customer_name);

        }
    }
}
