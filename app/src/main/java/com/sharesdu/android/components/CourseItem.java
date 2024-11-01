package com.sharesdu.android.components;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import de.hdodenhof.circleimageview.CircleImageView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.sharesdu.android.PageActivity;
import com.sharesdu.android.R;

import java.util.Map;

public class CourseItem extends LinearLayout {
    private TextView title_text,num_text,score_text,time_text;
    private LinearLayout click_linear;
    // 构造函数
    public CourseItem(@NonNull Context context, @NonNull AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CourseItem(@NonNull Context context, Map<String, Object> data) {
        super(context);
        init(context);
        populateData(data);
    }
    public CourseItem(@NonNull Context context) {
        super(context);
        init(context);
    }
    // 初始化视图
    private void init(Context context) {
        // 通过布局填充器加载布局
        LayoutInflater.from(context).inflate(R.layout.component_course_item, this, true);
        click_linear=findViewById(R.id.course_item_click_linear);
        click_linear.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent tmp=new Intent(context, PageActivity.class);
                context.startActivity(tmp);
            }
        });
    }

    // 使用字典数据填充视图
    private void populateData(Map<String, Object> data) {
    }
}

