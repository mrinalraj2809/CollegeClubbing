package com.example.collegeevent.Lokesh;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.collegeevent.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentFeed extends Fragment {


    public FragmentFeed() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fragment_feed, container, false);
    }

}
