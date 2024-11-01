package com.sharesdu.android.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.sharesdu.android.MainActivity;
import com.sharesdu.android.R;
import com.sharesdu.android.WeatherActivity;
import com.sharesdu.android.network.NetworkManager;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WeatherWidget extends AppWidgetProvider {
    public static int getWeatherIcon(String weatherCode, Context context) {
        // 将天气代码转换为小写
        String iconName = "weather_" + weatherCode.toLowerCase();
        // 获取资源 ID
        int resourceId = context.getResources().getIdentifier(iconName, "drawable", context.getPackageName());
        // 检查资源是否存在
        if (resourceId == 0) {
            // 返回默认图标或错误图标
            return R.drawable.weather_nan; // 假设你有一个默认图标
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
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {//fuck,fuck,fuck SB android
        super.onUpdate(context, appWidgetManager, appWidgetIds);
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_weather);
        // 创建一个 Intent，用于触发 onUpdate 方法
        Intent intent = new Intent(context, WeatherWidget.class);
        intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds);
        // 创建 PendingIntent，添加 FLAG_IMMUTABLE
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                context,
                0,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE // 添加 FLAG_IMMUTABLE
        );

        views.setOnClickPendingIntent(R.id.weather_widget_refresh_linear,pendingIntent);

// 创建一个 Intent 用于打开 MainActivity
        Intent intent2 = new Intent(context, MainActivity.class);
        intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent2.putExtra("weather",true);
// 使用 FLAG_IMMUTABLE 创建 PendingIntent
        PendingIntent pendingIntent2 = PendingIntent.getActivity(
                context,
                0,
                intent2,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
        );

// 将 PendingIntent 设置到按钮上
        views.setOnClickPendingIntent(R.id.weather_widget_top_linear, pendingIntent2);
        views.setOnClickPendingIntent(R.id.weather_widget_mid_linear, pendingIntent2);
        views.setOnClickPendingIntent(R.id.weather_widget_section_container, pendingIntent2);


        NetworkManager networkManager=new NetworkManager();
        String url="http://sharesdu.com:5000/weather?position=120.684594,36.366266&hourlysteps=24";
        networkManager.get(context, url, new NetworkManager.NetworkCallback() {
            @Override
            public void onSuccess(String result) {
                // 处理成功返回的数据
                Toast.makeText(context,"天气信息已更新",Toast.LENGTH_SHORT).show();
                Log.d("NetworkManager", "Success: " + result);
                try{
                    //deal with the request
                    JSONObject data=new JSONObject(result);
                    // 获取每日最高、最低温度
                    JSONObject r1 = (JSONObject) data.getJSONObject("result").getJSONObject("daily").getJSONArray("temperature").get(0);
                    String daily_max_temperature = formatTemperature(r1.getString("max"));
                    String daily_min_temperature = formatTemperature(r1.getString("min"));
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
                    // set last update time
                    views.setTextViewText(R.id.weather_widget_last_update_time_text,"天气信息上次更新于 "+getCurrentTimeFormatted());
                    //set title
                    views.setTextViewText(R.id.weather_widget_position_text, "青岛校区");
                    views.setTextViewText(R.id.weather_widget_info_text,now_condition_description+" "+daily_min_temperature+"°/"+daily_max_temperature+"°");
                    views.setTextViewText(R.id.weather_widget_temperature_text,now_temperature+"°");
                    views.setImageViewResource(R.id.weather_widget_image_button,getWeatherIcon(now_skycon,context));
                    //set hour time
                    // 设置小时信息
                    for (int i = 0; i < hour_time.size() && i < 6; i++) {
                        views.setTextViewText(context.getResources().getIdentifier("section_weather_time_text" + (i + 1), "id", context.getPackageName()), hour_time.get(i));
                        views.setTextViewText(context.getResources().getIdentifier("section_weather_temperature_text" + (i + 1), "id", context.getPackageName()), hour_temperature.get(i)+"°");
                        views.setImageViewResource(context.getResources().getIdentifier("section_weather_image_button" + (i + 1), "id", context.getPackageName()), getWeatherIcon(hour_skyicon.get(i), context));
                    }
                    appWidgetManager.updateAppWidget(appWidgetIds, views);
                }catch (Exception e){
                    Toast.makeText(context,"error get msg",Toast.LENGTH_SHORT).show();
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
}
