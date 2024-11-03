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

public class CoursePage extends LinearLayout {
    private TextView title, teacher, campus, type, college, method, score;
    private RatingBar rate;
    private LinearLayout comment_container;
    private HorizontalBarChart chart;

    // 构造函数
    public CoursePage(@NonNull Context context, @NonNull AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CoursePage(@NonNull Context context, Map<String, Object> data) {
        super(context);
        init(context);
        populateData(data);
    }

    public CoursePage(@NonNull Context context) {
        super(context);
        init(context);
    }

    // 初始化视图
    private void init(Context context) {
        // 通过布局填充器加载布局
        LayoutInflater.from(context).inflate(R.layout.component_course_page, this, true);
        // 绑定视图对象
        title = findViewById(R.id.course_page_title_text);
        teacher = findViewById(R.id.course_page_teacher_text);
        campus = findViewById(R.id.course_page_campus_text);
        type = findViewById(R.id.course_page_type_text);
        college = findViewById(R.id.course_page_college_text);
        method = findViewById(R.id.course_page_method_text);
        score = findViewById(R.id.course_page_score_text);
        rate = findViewById(R.id.course_page_rate);
        chart = findViewById(R.id.course_page_chart);
        comment_container=findViewById(R.id.course_page_comment_container);
        //
        chart.setDescription(null);
        setX();
        setY();
        loadData();

    }

    // 使用字典数据填充视图
    private void populateData(Map<String, Object> data) {
        if (data != null) {
        }
    }
    //添加评论的接口
    public void add_comment(View view){
        this.comment_container.addView(view);
    }
    //对柱状图操作的函数
    private void setX() {
        XAxis xAxis = chart.getXAxis();
        xAxis.setTextSize(12f);
        Typeface typeface = Typeface.create("sans-serif", Typeface.BOLD);
        xAxis.setTypeface(typeface);
        xAxis.setTextColor(getResources().getColor(R.color.mainColor));
        xAxis.setAxisMaximum(5);
        xAxis.setAxisMinimum(-0.5f);
        xAxis.setDrawGridLines(false);
        xAxis.setDrawAxisLine(false);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setLabelCount(5);

        final String[] labels = {"1", "2", "3", "4", "5"};

        xAxis.setValueFormatter(new ValueFormatter() {
            @Override
            public String getAxisLabel(float value, AxisBase axis) {
                int index = (int) value;
                if (index >= 0 && index < labels.length) {
                    return labels[index];
                }
                return ""; // 超出范围时返回空字符串
            }
        });
        xAxis.setXOffset(5f);
    }

    private void setY() {
        YAxis tepAxis = chart.getAxisLeft();
        tepAxis.setAxisMaximum(60f);
        tepAxis.setAxisMinimum(0f);
        tepAxis.setEnabled(false);
        YAxis yAxis = chart.getAxisRight();
        yAxis.setDrawAxisLine(false);
        yAxis.setDrawGridLines(false);
        yAxis.setDrawLabels(false);
        yAxis.setTextSize(0f);
        yAxis.setAxisMinimum(0f);
        yAxis.setAxisMaximum(60f);
        yAxis.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return Integer.toString((int)value);
            }
        });
    }

    private void loadData() {
        chart.getLegend().setEnabled(false);
        chart.setFitBars(true);
        chart.setExtraOffsets(0, 0, 10, 0);
        chart.setDrawValueAboveBar(true);
        final List<BarEntry> entries = new ArrayList<>();
        float[] da = {15f, 75f, 5f, 2f, 3f};
        for (int i = 0; i < da.length; i++) {
            entries.add(new BarEntry(i, da[i]));
        }
        BarDataSet barDataSet = new BarDataSet(entries, "");
        barDataSet.setValueTextSize(12f);
        barDataSet.setValueTextColor(Color.parseColor("#ffffff"));
        barDataSet.setColor(getResources().getColor(R.color.mainColor));
        barDataSet.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
                return Integer.toString((int)value);//保留两位小数
            }
        });
        BarData barData = new BarData(barDataSet);
        barData.setBarWidth(0.6f);
        chart.setData(barData);
        chart.setFitBars(true);
        chart.invalidate();
    }


}
