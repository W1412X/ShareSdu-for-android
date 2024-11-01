package com.sharesdu.android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sharesdu.android.components.CustomWebView;
import com.sharesdu.android.components.MyScrollView;
import com.sharesdu.android.network.NetworkManager;
import com.sharesdu.android.widget.weather_section.WeatherSection;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class WeatherActivity extends AppCompatActivity {
    private TextView temperature_now_text,position_text,temperature_today_text,temperature_tomorrow_text,skycon_now_text;
    private ImageView skycon_now_image,skycon_today_image,skycon_tomorrow_image;
    private LinearLayout hours_predict_container;
    private Button submit_token_btn;
    private EditText token_input;
    private Button inquire_bus_btn;
    private ImageView qd_bus_image;//这个应用的体积加起来还没这个图片大
    private CustomWebView other_bus_web;
    private MyScrollView bus_scroll_view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        initView();
        update();
    }
    private void initView(){
        this.temperature_now_text=findViewById(R.id.weather_activity_temperature_text);
        this.position_text=findViewById(R.id.weather_activity_position_text);
        this.skycon_now_text=findViewById(R.id.weather_activity_skycon_now_text);
        this.skycon_now_image=findViewById(R.id.weather_activity_skycon_image_view);
        this.hours_predict_container=findViewById(R.id.weather_activity_hour_section_container);
        this.submit_token_btn=findViewById(R.id.weather_activity_weather_token_button);
        this.temperature_today_text=findViewById(R.id.weather_activity_today_temperature_text);
        this.temperature_tomorrow_text=findViewById(R.id.weather_activity_tomorrow_temperature_text);
        this.token_input=findViewById(R.id.weather_activity_token_input);
        this.skycon_today_image=findViewById(R.id.weather_activity_today_icon_image);
        this.skycon_tomorrow_image=findViewById(R.id.weather_activity_tomorrow_icon_image);
        this.qd_bus_image=findViewById(R.id.weather_activity_qd_bus_image);
        this.inquire_bus_btn=findViewById(R.id.weather_activity_inquire_bus_button);
        this.bus_scroll_view=findViewById(R.id.weather_activity_bus_scrollview);
        this.other_bus_web=findViewById(R.id.weather_activity_other_bus_web);
        this.submit_token_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"功能暂未开放",Toast.LENGTH_SHORT).show();
            }
        });
        this.inquire_bus_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(other_bus_web.getVisibility()== View.GONE&&qd_bus_image.getVisibility()==View.GONE){
                    bus_scroll_view.setVisibility(View.VISIBLE);
                    qd_bus_image.setVisibility(View.VISIBLE);
                    inquire_bus_btn.setText("查询其他校区公交");
                }else{
                    if(other_bus_web.getVisibility()==View.GONE){
                        qd_bus_image.setVisibility(View.GONE);
                        other_bus_web.setVisibility(View.VISIBLE);
                        other_bus_web.loadUrl("https://bus.sdu.edu.cn/#/index");
                        inquire_bus_btn.setText("查询青岛校区公交");
                    }else{
                        qd_bus_image.setVisibility(View.VISIBLE);
                        other_bus_web.setVisibility(View.GONE);
                        inquire_bus_btn.setText("查询其他校区公交");
                    }
                }
            }
        });
    }
    private void update(){
        this.hours_predict_container.removeAllViews();
        NetworkManager networkManager=new NetworkManager();
        String url="http://sharesdu.com:5000/weather?position=120.684594,36.366266&hourlysteps=24";
        networkManager.get(getApplicationContext(), url, new NetworkManager.NetworkCallback() {
            @Override
            public void onSuccess(String result) {
                // 处理成功返回的数据
                Toast.makeText(getApplicationContext(),"天气信息已更新",Toast.LENGTH_SHORT).show();
                Log.d("NetworkManager", "Success: " + result);
                try{
                    //deal with the request
                    JSONObject data=new JSONObject(result);
                    // 获取每日最高、最低温度
                    JSONObject r1 = (JSONObject) data.getJSONObject("result").getJSONObject("daily").getJSONArray("temperature").get(0);
                    String today_max_temperature = formatTemperature(r1.getString("max"));
                    String today_min_temperature = formatTemperature(r1.getString("min"));
                    JSONObject r2 = (JSONObject) data.getJSONObject("result").getJSONObject("daily").getJSONArray("temperature").get(1);
                    String tomorrow_max_temperature = formatTemperature(r2.getString("max"));
                    String tomorrow_min_temperature = formatTemperature(r2.getString("min"));
                    //
                    JSONObject r3=(JSONObject) data.getJSONObject("result").getJSONObject("daily").getJSONArray("skycon").get(0);
                    JSONObject r4=(JSONObject) data.getJSONObject("result").getJSONObject("daily").getJSONArray("skycon").get(1);
                    String today_skycon=r3.getString("value");
                    String tomorrow_skycon=r3.getString("value");
                    skycon_today_image.setImageResource(getWeatherIcon(today_skycon,getApplicationContext()));
                    skycon_tomorrow_image.setImageResource(getWeatherIcon(tomorrow_skycon,getApplicationContext()));
                    // 获取当前天气状态描述
                    String now_condition_description = weatherCodeMap.getOrDefault(data.getJSONObject("result").getJSONObject("realtime").getString("skycon"), "未知");
                    String now_temperature = formatTemperature(data.getJSONObject("result").getJSONObject("realtime").getString("temperature"));
                    String now_skycon = data.getJSONObject("result").getJSONObject("realtime").getString("skycon");
                    // 初始化存储每小时的时间、天气图标和温度的数组
                    ArrayList<String> hour_time = new ArrayList<>();
                    ArrayList<String> hour_skyicon = new ArrayList<>();
                    ArrayList<String> hour_temperature = new ArrayList<>();
                    JSONArray array_temperature = data.getJSONObject("result").getJSONObject("hourly").getJSONArray("temperature");
                    JSONArray array_skycon = data.getJSONObject("result").getJSONObject("hourly").getJSONArray("skycon");
                    // 遍历每小时的温度数据
                    for (int i = 0; i < array_temperature.length(); i++) {
                        String t = array_temperature.getJSONObject(i).getString("datetime");
                        // 找到时间部分的起始和结束索引
                        int startIndex = t.indexOf('T') + 1; // 'T' 的下一个字符是时间部分的开始
                        int endIndex = startIndex + 5; // 时间部分是 5 个字符（hh:mm）
                        // 提取时间部分
                        String hour = t.substring(startIndex, endIndex);
                        hour_time.add(hour);
                        // 格式化温度为一位小数
                        String temperature = formatTemperature(array_temperature.getJSONObject(i).getString("value"));
                        hour_temperature.add(temperature);
                        String skycon = array_skycon.getJSONObject(i).getString("value");
                        hour_skyicon.add(skycon);
                    }
                    //
                    temperature_now_text.setText(now_temperature+"°");
                    skycon_now_text.setText(now_condition_description);
                    skycon_now_image.setImageResource(getWeatherIcon(now_skycon,getApplicationContext()));
                    temperature_today_text.setText(today_min_temperature+"° ~ "+today_max_temperature+"°");
                    temperature_tomorrow_text.setText(tomorrow_min_temperature+"° ~ "+tomorrow_max_temperature+"°");

                    //create
                    for (int i=0;i<hour_time.size();i++){
                        WeatherSection tmp=new WeatherSection(getApplicationContext());
                        tmp.setTime(hour_time.get(i));
                        tmp.setTemperature(hour_temperature.get(i)+"°");
                        tmp.setWeatherIcon(getWeatherIcon(hour_skyicon.get(i),getApplicationContext()));
                        hours_predict_container.addView(tmp);
                    }
                }catch (Exception e){
                    Toast.makeText(getApplicationContext(),"error get msg",Toast.LENGTH_SHORT).show();
                    Log.e("Weather get error","get error");
                }

            }
            @Override
            public void onFailure(IOException e) {
                // 处理请求失败
                Log.e("NetworkManager", "Failed: " + e.getMessage());
            }
            @Override
            public void onError(int code, String message) {
                // 处理错误情况
                Log.e("NetworkManager", "Error Code: " + code + ", Error Message: " + message);
            }
        });
    }
    public static int getWeatherIcon(String weatherCode, Context context) {
        // 将天气代码转换为小写
        String iconName = "weather_" + weatherCode.toLowerCase();
        // 获取资源 ID
        int resourceId = context.getResources().getIdentifier(iconName, "drawable", context.getPackageName());
        // 检查资源是否存在
        if (resourceId == 0) {
            // 返回默认图标或错误图标
            return R.drawable.weather_nan;
        }
        return resourceId;
    }
    // 格式化温度为一位小数的函数
    private static String formatTemperature(String temperature) {
        try {
            double tempValue = Double.parseDouble(temperature);
            DecimalFormat df = new DecimalFormat("#.#"); // 保留一位小数
            return df.format(tempValue);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return "0.0"; // 在发生异常时返回默认值
        }
    }
    private static final HashMap<String, String> weatherCodeMap = new HashMap<>();

    static {
        // 填充天气代码和描述
        weatherCodeMap.put("CLEAR_DAY", "晴（白天）");
        weatherCodeMap.put("CLEAR_NIGHT", "晴（夜间）");
        weatherCodeMap.put("PARTLY_CLOUDY_DAY", "多云（白天）");
        weatherCodeMap.put("PARTLY_CLOUDY_NIGHT", "多云（夜间）");
        weatherCodeMap.put("CLOUDY", "阴");
        weatherCodeMap.put("LIGHT_HAZE", "轻度雾霾");
        weatherCodeMap.put("MODERATE_HAZE", "中度雾霾");
        weatherCodeMap.put("HEAVY_HAZE", "重度雾霾");
        weatherCodeMap.put("LIGHT_RAIN", "小雨");
        weatherCodeMap.put("MODERATE_RAIN", "中雨");
        weatherCodeMap.put("HEAVY_RAIN", "大雨");
        weatherCodeMap.put("STORM_RAIN", "暴雨");
        weatherCodeMap.put("FOG", "雾");
        weatherCodeMap.put("LIGHT_SNOW", "小雪");
        weatherCodeMap.put("MODERATE_SNOW", "中雪");
        weatherCodeMap.put("HEAVY_SNOW", "大雪");
        weatherCodeMap.put("STORM_SNOW", "暴雪");
        weatherCodeMap.put("DUST", "浮尘");
        weatherCodeMap.put("SAND", "沙尘");
        weatherCodeMap.put("WIND", "大风");
    }
    public static String getCurrentTimeFormatted() {
        // 获取当前时间
        Calendar calendar = Calendar.getInstance();
        // 定义时间格式
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        // 格式化当前时间为字符串
        return sdf.format(calendar.getTime());
    }
    //这里主要是接收从main activity跳转过来的数据，跳转更新
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        // 处理新的 Intent 数据
        if(getIntent().getBooleanExtra("update",false)){
            update();
        }
    }
}