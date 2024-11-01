
package com.sharesdu.android.components;

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.sharesdu.android.R;
import java.util.ArrayList;

public class SimpleItemContainer extends LinearLayout {
    private Button switch_article_btn, switch_question_btn, switch_course_btn;
    private LinearLayout article_bar, question_bar, course_bar, container;
    private ArrayList<ArticleItem> article_list = new ArrayList<>();
    private ArrayList<QuestionItem> question_list=new ArrayList<>();
    private ArrayList<CourseItem> course_list=new ArrayList<>();
    private String type="article";
    // 构造函数（1）
    public SimpleItemContainer(@NonNull Context context) {
        super(context);
        init(context);
    }

    // 构造函数（2）
    public SimpleItemContainer(@NonNull Context context, @NonNull AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    // 构造函数（3）
    public SimpleItemContainer(@NonNull Context context, @NonNull AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    // 初始化方法
    private void init(Context context) {
        // 使用 LayoutInflater 来加载自定义布局
        LayoutInflater.from(context).inflate(R.layout.component_simple_item_container, this, true);
        this.switch_article_btn = findViewById(R.id.simple_item_container_article_button);
        this.switch_course_btn = findViewById(R.id.simple_item_container_course_button);
        this.switch_question_btn = findViewById(R.id.simple_item_container_question_button);
        this.article_bar = findViewById(R.id.simple_item_container_article_bar);
        this.question_bar = findViewById(R.id.simple_item_container_question_bar);
        this.course_bar = findViewById(R.id.simple_item_container_course_bar);
        this.container = findViewById(R.id.simple_item_container_container);
        switch_article_btn.setOnClickListener(v -> selectArticle());
        switch_course_btn.setOnClickListener(v -> selectCourse());
        switch_question_btn.setOnClickListener(v -> selectQuestion());
    }
    private void onSwipeLeft() {
        // 处理左滑
        if(type=="article"){
            selectQuestion();
        }else if(type=="question"){
            selectCourse();
        }else{
            selectArticle();
        }
    }

    private void onSwipeRight() {
        // 处理右滑
        if(type=="article"){
            selectCourse();
        }else if(type=="course"){
            selectArticle();
        }else{
            selectQuestion();
        }
    }

    private void selectArticle() {
        type="article";
        container.removeAllViews();
        //to do add articles
        for (int i = 0; i < article_list.size(); i++) {
            container.addView(article_list.get(i));
        }
        setButtonState(switch_article_btn, article_bar, true);
        setButtonState(switch_course_btn, course_bar, false);
        setButtonState(switch_question_btn, question_bar, false);
    }

    private void selectCourse() {
        type="course";
        container.removeAllViews();
        //to do, add courses
        for (int i = 0; i < course_list.size(); i++) {
            container.addView(course_list.get(i));
        }
        setButtonState(switch_article_btn, article_bar, false);
        setButtonState(switch_course_btn, course_bar, true);
        setButtonState(switch_question_btn, question_bar, false);
    }

    private void selectQuestion() {
        type="question";
        container.removeAllViews();
        //to do, add questions
        for (int i = 0; i < question_list.size(); i++) {
            container.addView(question_list.get(i));
        }
        setButtonState(switch_article_btn, article_bar, false);
        setButtonState(switch_course_btn, course_bar, false);
        setButtonState(switch_question_btn, question_bar, true);
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

    public void switch_type(String type) {
        if (type.equals("article")) {
            selectArticle();
        } else if (type.equals("question")) {
            selectQuestion();
        } else {
            selectCourse();
        }
    }

    public void add_question(QuestionItem item) {
        this.selectQuestion();
        container.addView(item);
        this.question_list.add(item);
    }

    public void add_article(ArticleItem item) {
        this.selectArticle();
        container.addView(item);
        this.article_list.add(item);
    }

    public void add_course(CourseItem item) {
        this.selectCourse();
        container.addView(item);
        this.course_list.add(item);
    }
}