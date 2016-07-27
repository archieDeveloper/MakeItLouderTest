package com.shahbazly_dev.makeitlouder;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class Fragment_welcome extends Fragment {

    private String title;
    private int page;

    public static Fragment_welcome newInstance(int page, String title) {
        Fragment_welcome fragment_welcome = new Fragment_welcome();
        Bundle args = new Bundle();
        args.putInt("Int", page);
        args.putString("Title", title);
        fragment_welcome.setArguments(args);
        return fragment_welcome;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        page = getArguments().getInt("Int", 0);
        title = getArguments().getString("Title");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.welcome, container, false);
        return view;
    }
}
