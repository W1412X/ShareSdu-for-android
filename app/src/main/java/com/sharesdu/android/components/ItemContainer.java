package com.sharesdu.android.components;
import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import com.sharesdu.android.R;
import com.sharesdu.android.components.adapter.ViewPagerAdapter;
import com.sharesdu.android.components.fragment.ArticleListFragment;
import com.sharesdu.android.components.fragment.CourseListFragment;
import com.sharesdu.android.components.fragment.QuestionListFragment;

import java.util.ArrayList;

public class ItemContainer extends LinearLayout {
    private Button switch_article_btn, switch_question_btn, switch_course_btn;
    private LinearLayout article_bar, question_bar, course_bar;
    public ItemContainer(@NonNull Context context) {
        super(context);
        init(context);
    }

    public ItemContainer(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ItemContainer(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        // 使用 LayoutInflater 加载布局
        LayoutInflater.from(context).inflate(R.layout.component_item_container, this, true);

        // 初始化控件
        switch_article_btn = findViewById(R.id.item_container_article_button);
        switch_course_btn = findViewById(R.id.item_container_course_button);
        switch_question_btn = findViewById(R.id.item_container_question_button);
        article_bar = findViewById(R.id.item_container_article_bar);
        question_bar = findViewById(R.id.item_container_question_bar);
        course_bar = findViewById(R.id.item_container_course_bar);

        // 初始化 ViewPager
        ViewPager viewPager = findViewById(R.id.item_container_view_pager);
        FragmentManager manager=((FragmentActivity) context).getSupportFragmentManager();
        ViewPagerAdapter adapter = new ViewPagerAdapter(((FragmentActivity) context).getSupportFragmentManager());
        // 将 Fragment 添加到适配器
        adapter.addFragment(new ArticleListFragment(), "Article");
        adapter.addFragment(new QuestionListFragment(), "Question");
        adapter.addFragment(new CourseListFragment(), "Course");

        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(2);
        // 监听 ViewPager 滑动事件
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

            @Override
            public void onPageSelected(int position) {
                // 更新按钮状态
                switch (position) {
                    case 0:
                        setButtonState(switch_article_btn, article_bar, true);
                        setButtonState(switch_question_btn, question_bar, false);
                        setButtonState(switch_course_btn, course_bar, false);
                        break;
                    case 1:
                        setButtonState(switch_article_btn, article_bar, false);
                        setButtonState(switch_question_btn, question_bar, true);
                        setButtonState(switch_course_btn, course_bar, false);
                        break;
                    case 2:
                        setButtonState(switch_article_btn, article_bar, false);
                        setButtonState(switch_question_btn, question_bar, false);
                        setButtonState(switch_course_btn, course_bar, true);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {}
        });

        // 设置按钮点击事件切换 ViewPager 页面
        switch_article_btn.setOnClickListener(v -> viewPager.setCurrentItem(0));
        switch_question_btn.setOnClickListener(v -> viewPager.setCurrentItem(1));
        switch_course_btn.setOnClickListener(v -> viewPager.setCurrentItem(2));
    }

    private void setButtonState(Button button, View bar, boolean isSelected) {
        if (isSelected) {
            button.setTextColor(getResources().getColor(R.color.mainColor)); // 设置按钮文本颜色为主色
            bar.setBackgroundColor(getResources().getColor(R.color.mainColor)); // 设置 bar 背景颜色为主色
        } else {
            button.setTextColor(getResources().getColor(R.color.gray)); // 设置按钮文本颜色为灰色
            bar.setBackgroundColor(getResources().getColor(R.color.gray)); // 设置 bar 背景颜色为灰色
        }
    }
}
