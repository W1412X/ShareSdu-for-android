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
import com.sharesdu.android.components.QuestionItem;

import java.util.ArrayList;

public class QuestionListFragment extends Fragment {
    private ArrayList<QuestionItem> question_list = new ArrayList<>();
    private LinearLayout container;
    public QuestionListFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_question_list, container, false);
        return view;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.container = view.findViewById(R.id.fragment_question_list_container);
        for(int i=0;i<20;i++){
            this.add_question(new QuestionItem(requireContext()));
        }
    }
    public void add_question(QuestionItem item){
        this.question_list.add(item);
        this.container.addView(item);
    }
}
