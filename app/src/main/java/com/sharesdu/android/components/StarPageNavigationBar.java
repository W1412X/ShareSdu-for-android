package com.sharesdu.android.components;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.navigation.NavigationView;
import com.sharesdu.android.R;
import com.sharesdu.android.WeatherActivity;

public class StarPageNavigationBar extends NavigationView {

    //weather part
    private LinearLayout weather_msg_view;
    private Button weather_btn;
    //clause part
    private Button clause_btn;
    private LinearLayout clause_msg_view;
    //app part
    private Button app_button;
    private LinearLayout app_msg_view;
    //contact part
    private Button contact_btn;
    private LinearLayout contact_msg_view;

    public StarPageNavigationBar(@NonNull Context context) {
        super(context);
        init(context);
    }

    public StarPageNavigationBar(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public StarPageNavigationBar(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        // Inflate custom navigation bar layout
        LayoutInflater.from(context).inflate(R.layout.component_star_page_navigation_bar, this, true);


        //weather
        this.weather_btn=findViewById(R.id.star_page_setting_bar_weather_button);
        this.weather_msg_view=findViewById(R.id.star_page_setting_bar_weather_message_view);
        this.weather_btn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if(weather_msg_view.getVisibility()==View.VISIBLE){
                    weather_msg_view.setVisibility(View.GONE);
                }else{
                    weather_msg_view.setVisibility(View.VISIBLE);
                }
            }
        });
        this.weather_msg_view.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                context.startActivity(new Intent(context, WeatherActivity.class));
            }
        });

        //clause
        this.clause_btn=findViewById(R.id.star_page_setting_bar_clause_button);
        this.clause_msg_view=findViewById(R.id.star_page_setting_bar_clause_message_view);
        this.clause_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(clause_msg_view.getVisibility()==View.VISIBLE){
                    clause_msg_view.setVisibility(View.GONE);
                }else{
                    clause_msg_view.setVisibility(View.VISIBLE);
                }
            }
        });
        this.clause_msg_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://sharesdu.com"));
                context.startActivity(browserIntent);
            }
        });
        //app
        this.app_button=findViewById(R.id.star_page_setting_bar_app_button);
        this.app_msg_view=findViewById(R.id.star_page_setting_bar_app_message_view);
        this.app_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(app_msg_view.getVisibility()==View.VISIBLE){
                    app_msg_view.setVisibility(View.GONE);
                }else{
                    app_msg_view.setVisibility(View.VISIBLE);
                }
            }
        });
        this.app_msg_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://sharesdu.com"));
                context.startActivity(browserIntent);
            }
        });
        //contact
        this.contact_btn=findViewById(R.id.star_page_setting_bar_contact_button);
        this.contact_msg_view=findViewById(R.id.star_page_setting_bar_contact_message_view);
        this.contact_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(contact_msg_view.getVisibility()==View.VISIBLE){
                    contact_msg_view.setVisibility(View.GONE);
                }else{
                    contact_msg_view.setVisibility(View.VISIBLE);
                }
            }
        });
        this.contact_msg_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("mailto:admin@sharesdu.com"));
                context.startActivity(browserIntent);
            }
        });
    }
}
