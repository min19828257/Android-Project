package com.example.samsung.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class MainActivity extends Fragment {
    public static MainActivity newInstance(){
        MainActivity f = new MainActivity();

        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState)
    {
        View layout = inflater.inflate(R.layout.activity_main,container, false);

        return layout;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);

        setView(view);
    }

    public void setView(View view) {
        TextView titleText = view.findViewById(R.id.title);
        titleText.setText("안녕");
    }
}
