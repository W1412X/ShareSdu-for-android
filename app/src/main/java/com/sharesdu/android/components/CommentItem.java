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

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CommentItem extends LinearLayout {
    private TextView name,text;
    private RatingBar rate;

    // 构造函数
    public CommentItem(@NonNull Context context, @NonNull AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CommentItem(@NonNull Context context, Map<String, Object> data) {
        super(context);
        init(context);
        populateData(data);
    }

    public CommentItem(@NonNull Context context) {
        super(context);
        init(context);
    }

    // 初始化视图
    private void init(Context context) {
        // 通过布局填充器加载布局
        LayoutInflater.from(context).inflate(R.layout.component_comment_item, this, true);
        // 绑定视图对象
        name=findViewById(R.id.component_comment_name);
        text=findViewById(R.id.component_comment_text);
        rate=findViewById(R.id.component_comment_rate);

    }
    // 使用字典数据填充视图
    private void populateData(Map<String, Object> data) {
        if (data != null) {
        }
    }
    public void setRateVisibility(int visibility){
        rate.setVisibility(visibility);
    }
}
