package com.sharesdu.android.components;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

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

public class QuestionEditor extends LinearLayout {
    private EditText title,detail;
    private Button submit,cancel;
    // 构造函数
    public QuestionEditor(@NonNull Context context, @NonNull AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public QuestionEditor(@NonNull Context context, Map<String, Object> data) {
        super(context);
        init(context);
    }

    public QuestionEditor(@NonNull Context context) {
        super(context);
        init(context);
    }

    // 初始化视图
    private void init(Context context) {
        // 通过布局填充器加载布局
        LayoutInflater.from(context).inflate(R.layout.component_question_editor, this, true);
        // 绑定视图对象
        title=findViewById(R.id.component_question_editor_title_input);
        detail=findViewById(R.id.component_question_editor_detail_input);
        submit=findViewById(R.id.component_question_editor_submit_button);
        cancel=findViewById(R.id.component_question_editor_cancel_button);
        submit.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,"发布成功",Toast.LENGTH_SHORT).show();
                setVisibility(View.GONE);
            }
        });
        cancel.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                setVisibility(View.GONE);
            }
        });
    }

}
