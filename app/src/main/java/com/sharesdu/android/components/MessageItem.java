package com.sharesdu.android.components;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;
import de.hdodenhof.circleimageview.CircleImageView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.sharesdu.android.R;

import java.util.Map;

public class MessageItem extends LinearLayout {
    private TextView title_text,author_text,info_text,time_text;
    private CircleImageView profile_image;
    // 构造函数
    public MessageItem(@NonNull Context context, @NonNull AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public MessageItem(@NonNull Context context, Map<String, Object> data) {
        super(context);
        init(context);
        populateData(data);
    }
    public MessageItem(@NonNull Context context) {
        super(context);
        init(context);
    }
    // 初始化视图
    private void init(Context context) {
        // 通过布局填充器加载布局
        LayoutInflater.from(context).inflate(R.layout.component_message_item, this, true);
    }

    // 使用字典数据填充视图
    private void populateData(Map<String, Object> data) {
    }
}

