package com.sharesdu.android;
/*
 * 其实吧，变量名也挺纠结命名方式的，下划线还是驼峰？
 * 还有函数名
 * 主要怎么说呢，个人习惯下划线，但是之前一个VUe项目又用驼峰，淦，其实是有点混乱的
 */
/*
 * 这个页面就是包含了文章页面，问题页面，课程页面
 * 也就是只有中间的内容区域会对于不同的类型做调整，其余的部分都是共用的
 * 对于文章页面
 * 因为比较复杂，还要分文章编辑的时候用的文章类型(夫文本和md)所以内容显示套壳网页
 * 其他两个目前是打算直接原生的，也没啥计数难点
 * 但是突然发现文章和问题、课程显示格式还不一样。这让我怎么改？
 * 最近不咋有时间，决定这样高
 * 对于文章，提供收藏，分享按钮
 * 对于问题，提供所有按钮
 * 对于课程，提供所有按钮，但是对于作者的显示统一为sharesdu
 */
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.sharesdu.android.components.CommentItem;
import com.sharesdu.android.components.CoursePage;
import com.sharesdu.android.components.QuestionPage;

import org.json.JSONObject;

import de.hdodenhof.circleimageview.CircleImageView;

public class PageActivity extends AppCompatActivity {
    private TextView title_text,author_text;
    private ImageButton star_btn,comment_btn,share_btn,send_btn;
    private EditText comment_input;
    private LinearLayout editor_linear,bottom_linear;
    private CircleImageView author_profile;
    private LinearLayout content_container;
    private RatingBar rate_bar;
    //这个用于控制评论输入框上方的评分部分是否显示
    private LinearLayout rate_linear;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page);
        initView();
        //接收跳转数据
        handle_intent(getIntent());
    }
    private void initView(){
        title_text=findViewById(R.id.page_page_top_title_text);
        author_text=findViewById(R.id.page_page_author_name_text);
        star_btn=findViewById(R.id.page_page_star_img_btn);
        comment_btn=findViewById(R.id.page_page_comment_img_btn);
        share_btn=findViewById(R.id.page_page_share_img_btn);
        author_profile=findViewById(R.id.page_page_author_profile_circle_image);
        content_container=findViewById(R.id.page_page_content_linear);
        editor_linear=findViewById(R.id.page_page_comment_edit_linear);
        bottom_linear=findViewById(R.id.page_page_bottom_bar_tool_linear);
        rate_linear=findViewById(R.id.course_page_rate_linear);
        //分享按钮
        share_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"此功能暂未开通",Toast.LENGTH_SHORT).show();
            }
        });
        //收藏按钮
        share_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"此功能暂未开通",Toast.LENGTH_SHORT).show();
            }
        });
    }
    /*
     * 其实这里在想要不要做acitvity复用，
     * 这里不像之前的主页，
     * 那个为了保持原来的浏览位置必须做，
     * 这里感觉不考虑性能可以不做。
     */
    //这个页面被打开的要求必须接收json信息
    private void handle_intent(Intent intent){
        // 处理新的 Intent 数据
        String str=intent.getStringExtra("page_data");
        if(str==null){
            Toast.makeText(getApplicationContext(),"应用错误，请联系开发者",Toast.LENGTH_SHORT).show();
            return;
        }
        try{
            JSONObject data=new JSONObject(str);
            //在这里进行数据装配
            //首先判断页面类型
            switch (data.getString("type")){
                case "question":
                    load_question_page();
                    break;
                case "article":
                    load_article_page();
                    break;
                case "course":
                    load_course_page();
                    break;
                default:
                    Toast.makeText(getApplicationContext(),"应用错误，请联系开发者",Toast.LENGTH_SHORT).show();
                    return;
            }
        }catch (Exception e){
            Toast.makeText(getApplicationContext(),"应用错误，请联系开发者",Toast.LENGTH_SHORT).show();
            return;
        }
    }
    //这里完成不同界面的函数
    private void load_question_page(){//对于问题界面
        comment_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor_linear.setVisibility(View.VISIBLE);
                bottom_linear.setVisibility(View.GONE);
                rate_linear.setVisibility(View.GONE);
            }
        });
        QuestionPage p=new QuestionPage(getApplicationContext());
        content_container.addView(p);
        for(int i=0;i<10;i++){
            CommentItem ct=new CommentItem(getApplicationContext());
            ct.setRateVisibility(View.GONE);
            p.add_comment(ct);
        }
    }
    private void load_course_page(){
        comment_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor_linear.setVisibility(View.VISIBLE);
                bottom_linear.setVisibility(View.GONE);
                rate_linear.setVisibility(View.VISIBLE);
            }
        });
        CoursePage p= new CoursePage(getApplicationContext());
        content_container.addView(p);
        for(int i=0;i<10;i++){
            CommentItem ct=new CommentItem(getApplicationContext());
            p.add_comment(ct);
        }
    }
    private void load_article_page(){
        comment_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"APP暂不支持此功能",Toast.LENGTH_SHORT).show();
            }
        });
    }

}