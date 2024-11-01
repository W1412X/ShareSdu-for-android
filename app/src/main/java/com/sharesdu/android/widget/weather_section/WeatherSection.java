package com.sharesdu.android.widget.weather_section;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.Nullable;
import com.sharesdu.android.R;
public class WeatherSection extends LinearLayout {
    private TextView temperatureTextView;
    private TextView timeTextView;
    private ImageButton weatherImageButton;
    public WeatherSection(Context context) {
        super(context);
        init(context);
    }
    public WeatherSection(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }
    public WeatherSection(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }
    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.section_weather, this, true);
        // 初始化视图元素
        temperatureTextView = findViewById(R.id.section_weather_temperature_text);
        timeTextView = findViewById(R.id.section_weather_time_text);
        weatherImageButton = findViewById(R.id.section_weather_image_button);

    }
    // 设置温度
    public void setTemperature(String temperature) {
        temperatureTextView.setText(temperature);
    }
    // 设置时间
    public void setTime(String time) {
        timeTextView.setText(time);
    }
    // 设置天气图标
    public void setWeatherIcon(int resId) {
        weatherImageButton.setBackgroundResource(resId);
    }
}
