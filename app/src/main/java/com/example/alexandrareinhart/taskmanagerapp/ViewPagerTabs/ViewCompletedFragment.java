package com.example.alexandrareinhart.taskmanagerapp.ViewPagerTabs;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.alexandrareinhart.taskmanagerapp.R;

import butterknife.ButterKnife;

public class ViewCompletedFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_view_completed_recycler, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    public static ViewCompletedFragment newInstance() {

        Bundle args = new Bundle();

        ViewCompletedFragment fragment = new ViewCompletedFragment();
        fragment.setArguments(args);
        return fragment;
    }
}
