package com.sharesdu.android;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.sharesdu.android.components.QuestionEditor;
import com.sharesdu.android.main_page_parts.MainPageHome;
import com.sharesdu.android.main_page_parts.MainPageMessage;
import com.sharesdu.android.main_page_parts.MainPageStar;
import com.sharesdu.android.utils.PreferencesUtil;

public class MainActivity extends AppCompatActivity {
    private FragmentManager fragmentManager;
    private BottomNavigationView bottomNavigationView;
    private FragmentTransaction fragmentTransaction;
    private Fragment last_fragment;
    private long lastBackPressedTime = 0;
    private ImageButton create_btn;
    private QuestionEditor question_editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init_view();
        loadInitialFragment(); // Load the initial fragment on startup

        //if login
        String username=PreferencesUtil.getString(this,"username","");
        if(username==""){
            startActivity(new Intent(this,LoginActivity.class));
            finish();
            return;
        }

        //deal the data of intent
        handle_intent(getIntent());
    }
    //overwrite the back logic,exit application when press twice in 2 s
    @Override
    public void onBackPressed() {
        if (lastBackPressedTime + 2000 > SystemClock.elapsedRealtime()) {
            //if press return twice in 2 s,then exit
            finish();
            return;
        }
        //if first return
        lastBackPressedTime = SystemClock.elapsedRealtime();
        Toast.makeText(this, "再次点击返回键退出应用", Toast.LENGTH_SHORT).show();
    }
    void init_view() {
        // Initialize the bottom navigation view
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setItemIconTintList(null);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.hide(last_fragment);
            Fragment selectedFragment = null;
            if (item.getItemId() == R.id.menu_home) {
                selectedFragment = fragmentManager.findFragmentByTag("MainPageHome");
                if (selectedFragment == null) {
                    selectedFragment = new MainPageHome();
                    fragmentTransaction.add(R.id.main_fragment_container, selectedFragment, "MainPageHome");
                }
            } else if (item.getItemId() == R.id.menu_message) {
                selectedFragment = fragmentManager.findFragmentByTag("MainPageMessage");
                if (selectedFragment == null) {
                    selectedFragment = new MainPageMessage();
                    fragmentTransaction.add(R.id.main_fragment_container, selectedFragment, "MainPageMessage");
                }
            } else if (item.getItemId() == R.id.menu_star) {
                selectedFragment = fragmentManager.findFragmentByTag("MainPageStar");
                if (selectedFragment == null) {
                    selectedFragment = new MainPageStar();
                    fragmentTransaction.add(R.id.main_fragment_container, selectedFragment, "MainPageStar");
                }
            }
            last_fragment=selectedFragment;
            fragmentTransaction.show(selectedFragment);
            fragmentTransaction.commit();
            return true;
        });
        //
        question_editor=findViewById(R.id.main_activity_question_editor);
        create_btn=findViewById(R.id.main_activity_create_img_button);
        create_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                question_editor.setVisibility(View.VISIBLE);
            }
        });
    }

    private void loadInitialFragment() {
        // Load the initial fragment (home)
        this.fragmentManager = getSupportFragmentManager();
        this.fragmentTransaction = fragmentManager.beginTransaction();
        Fragment tmp=new MainPageHome();
        fragmentTransaction.add(R.id.main_fragment_container, tmp, "MainPageHome");
        this.last_fragment=tmp;
        fragmentTransaction.show(tmp);
        this.fragmentTransaction.commit();
    }
    //如何这个activity已经存在，读取intent数据(跳转weather activity)
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        handle_intent(intent);
    }
    private void handle_intent(Intent intent){
        // 处理新的 Intent 数据
        if(intent.getBooleanExtra("weather",false)){

            //这里就不再利用判断是否存在weather activity做优化了。感觉没啥必要
            //码的，还是优化以下，不过优化就要补充weather更新的逻辑，干
            Intent intent_tmp=new Intent(MainActivity.this,WeatherActivity.class);
            intent_tmp.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            intent_tmp.putExtra("update",true);
            startActivity(intent_tmp);
        }
    }
}
