package com.sharesdu.android.components;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.ViewPortHandler;
import com.sharesdu.android.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class QuestionPage extends LinearLayout {
    private TextView detail,time,title;
    private LinearLayout comment_container;
    // 构造函数
    public QuestionPage(@NonNull Context context, @NonNull AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public QuestionPage(@NonNull Context context, Map<String, Object> data) {
        super(context);
        init(context);
        populateData(data);
    }

    public QuestionPage(@NonNull Context context) {
        super(context);
        init(context);
    }

    // 初始化视图
    private void init(Context context) {
        // 通过布局填充器加载布局
        LayoutInflater.from(context).inflate(R.layout.component_question_page, this, true);
        // 绑定视图对象
        detail=findViewById(R.id.component_question_page_detail_text);
        time=findViewById(R.id.component_question_page_time_text);
        comment_container=findViewById(R.id.component_question_page_comment_container);
        title=findViewById(R.id.component_question_page_title_text);
    }

    // 使用字典数据填充视图
    public void populateData(Map<String, Object> data) {
        if (data != null) {
        }
    }
    //添加评论的接口
    public void add_comment(View view){
        this.comment_container.addView(view);
    }

}
