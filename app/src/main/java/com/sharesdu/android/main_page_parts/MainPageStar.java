package com.sharesdu.android.main_page_parts;
/*

 */
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.sharesdu.android.R;
import com.sharesdu.android.components.ArticleItem;
import com.sharesdu.android.components.CourseItem;
import com.sharesdu.android.components.QuestionItem;
import com.sharesdu.android.components.SimpleItemContainer;
import com.sharesdu.android.components.StarPageNavigationBar;
/*
艾，这里本来是想直接展示对应的列表的，但是复用ItemContainer的时候
发现以下BUG
使用在同一个的Activity中使用Fragment的时候，创建新的Fragment如果是创建的两个相同的类
貌似会直接使用之前的Fragment实例

找到BUG了，因为对LInearLayout设置了可点击，大概是点击事件被拦截了，
但是我也不想改LinearLayout自己在写一个LinearLayout类的，所以就暂时不提供滑动切换了

之所以不使用MainHomePage里用的类是因为另外一个坑，
因为fragment在同一个activity里面(是这个引起的，具体怎么改我忘了，但是我记得当时感觉很麻烦就不在这里实现了)，


Bug猜测原因
Fragment由FragmentManager同一管理，可能就是FragmentManager的问题，但是FragmentManager又没办法改，是直接由系统管理的
所以嘛，本来时要多写几个类的，但是，项目结构太复杂了，所以决定把starpage改成由一些组件组成，搞一个按钮是进入StarActivit的接口

码的，感觉用activity不利于我管理，最终决定，直接用LinearLayout吧，不提供滑动切换页面视图的功能了
其实所有的锅都是这个滑动切换的

 */
/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MainPageStar#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainPageStar extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private SimpleItemContainer container;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private StarPageNavigationBar setting_bar;
    private ImageButton setting_btn;
    public MainPageStar() {
        // Required empty public constructor
    }
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MainPageStar.
     */
    // TODO: Rename and change types and number of parameters
    public static MainPageStar newInstance(String param1, String param2) {
        MainPageStar fragment = new MainPageStar();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_main_page_star, container, false);
        return view;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        initView(view);
    }
    private View initView(View view){
        this.setting_bar=view.findViewById(R.id.star_page_setting_bar);
        this.setting_btn=view.findViewById(R.id.star_page_setting_button);
        this.container=view.findViewById(R.id.star_page_simple_item_container);
        for(int i=0;i<20;i++){
            this.container.add_article(new ArticleItem(requireContext()));
            this.container.add_course(new CourseItem(requireContext()));
            this.container.add_question(new QuestionItem(requireContext()));
        }
        this.setting_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setting_bar.setVisibility(View.VISIBLE);
            }
        });
        // 设置根视图的触摸事件监听
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (setting_bar.getVisibility() == View.VISIBLE) {//检查触摸事件是否在setting_bar之外
                    int[] location = new int[2];
                    setting_bar.getLocationOnScreen(location);//获取setting_bar的位置
                    float x = event.getRawX();//获取触摸点的X坐标
                    float y = event.getRawY();//获取触摸点的Y坐标
                    //检查触摸事件是否在setting_bar的边界内
                    if (x < location[0] || x > location[0] + setting_bar.getWidth() ||
                            y < location[1] || y > location[1] + setting_bar.getHeight()) {
                        setting_bar.setVisibility(View.GONE);//隐藏
                    }
                }
                return false;//返回false以便继续处理其他触摸事件
            }
        });
        return view;
    }
}