package com.sharesdu.android.components.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.sharesdu.android.R;
import com.sharesdu.android.components.ArticleItem;

import java.util.ArrayList;

public class ArticleListFragment extends Fragment {
    private ArrayList<ArticleItem> article_list = new ArrayList<>();
    private LinearLayout container;
    public ArticleListFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_article_list, container, false);
        return view;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.container = view.findViewById(R.id.fragment_article_list_container);
        for(Integer i=0;i<20;i++){
            Log.d("ADD",i.toString()+this.getId());
            this.add_article(new ArticleItem(requireContext()));
        }
    }
    public void add_article(ArticleItem item){
        this.article_list.add(item);
        this.container.addView(item);
    }
}
