package com.sharesdu.android;
/*
 * 其实吧，变量名也挺纠结命名方式的，下划线还是驼峰？
 * 还有函数名
 * 主要怎么说呢，个人习惯下划线，但是之前一个VUe项目又用驼峰，淦，其实是有点混乱的
 */
/*
 * 蹩脚英语，为什么一会英语一会汉语写注释，因为这个20版本的studio不支持中文输入
 * this activity aims to be reuse by article,course and question page
 * for article :
 * use web display the rendered content (PageWebView)
 * for question and course :
 * android origin view to display
 * for all :
 * the logic to comment or star or share realize in android
 * and about the data fetch
 * for every component, fetch and load the content they need seems better
 * however , may be burden the server
 */
import androidx.appcompat.app.AppCompatActivity;

import android.media.Image;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import de.hdodenhof.circleimageview.CircleImageView;

public class PageActivity extends AppCompatActivity {
    private TextView title_text,author_text;
    private ImageButton star_btn,comment_btn,share_btn;
    private CircleImageView author_profile;
    private LinearLayout content_container;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page);
    }
    private void initView(){
        title_text=findViewById(R.id.page_page_top_title_text);
        author_text=findViewById(R.id.page_page_author_name_text);
        star_btn=findViewById(R.id.page_page_star_img_btn);
        comment_btn=findViewById(R.id.page_page_comment_img_btn);
        share_btn=findViewById(R.id.page_page_share_img_btn);
        author_profile=findViewById(R.id.page_page_author_profile_circle_image);
        content_container=findViewById(R.id.page_page_content_linear);
    }
    /*
     * 其实这里在想要不要做acitvity复用，
     * 这里不像之前的主页，
     * 那个为了保持原来的浏览位置必须做，
     * 这里感觉不考虑性能可以不做。
     */

}