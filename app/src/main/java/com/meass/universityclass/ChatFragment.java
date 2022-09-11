package com.meass.universityclass;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


public class ChatFragment extends Fragment {

   View view;
    private String name, section, roomid, class_,uuid;
    public ChatFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_chat, container, false);

        Toast.makeText(view.getContext(), ""+name, Toast.LENGTH_SHORT).show();
        return view;
    }
}