package com.meass.universityclass;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import java.util.List;

public class getDataAdapter1 extends RecyclerView.Adapter<getDataAdapter1.myview> {
    public List<PDF_upload> data;

    public getDataAdapter1(List<PDF_upload> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public myview onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.pdf,parent,false);
        return new getDataAdapter1.myview(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myview holder, final int position) {
        holder.text.setText(data.get(position).getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1=new Intent(v.getContext(), Second_PdfView.class);
                intent1.putExtra("url",data.get(position).getUrl());
                intent1.putExtra("name",data.get(position).getName());
                v.getContext().startActivity(intent1);
            }
        });

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class myview extends RecyclerView.ViewHolder{
        TextView text;
        public myview(@NonNull View itemView) {
            super(itemView);
            text=itemView.findViewById(R.id.textViewTitle);

        }
    }
}
