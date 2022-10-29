package com.example.bakatoon;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class MbtiFragment extends Fragment {
    private View infoFragment;
    private Button mbtiInfoBtn;
    private Intent intent;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        infoFragment = inflater.inflate(R.layout.fragment_mbti, container, false);

        mbtiInfoBtn = infoFragment.findViewById(R.id.mbtiInfoBtn);

        mbtiInfoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(getActivity(), InfoMbtiActivity.class);
                startActivity(intent);
            }
        });
        return infoFragment;
    }
}