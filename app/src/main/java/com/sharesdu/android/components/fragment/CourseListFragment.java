package com.sharesdu.android.components.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.sharesdu.android.R;
import com.sharesdu.android.components.CourseItem;

import java.util.ArrayList;

public class CourseListFragment extends Fragment {
    private ArrayList<CourseItem> course_list = new ArrayList<>();
    private LinearLayout container;
    public CourseListFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_course_list, container, false);

        return view;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.container = view.findViewById(R.id.fragment_course_list_container);
        for(int i=0;i<20;i++){
            this.add_course(new CourseItem(requireContext()));
        }
    }
    public void add_course(CourseItem item){
        this.course_list.add(item);
        this.container.addView(item);
    }
}
