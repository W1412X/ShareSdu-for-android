package com.sharesdu.android.main_page_parts;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.sharesdu.android.R;
import com.sharesdu.android.components.ItemContainer;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MainPageHome#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainPageHome extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    //search part
    private ImageButton search_btn;
    private EditText search_input;
    private Spinner search_type_spinner;
    private String[] search_types={"文章","问答","课程"};
    private ItemContainer item_container;
    public MainPageHome() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MainPageHome.
     */
    // TODO: Rename and change types and number of parameters
    public static MainPageHome newInstance(String param1, String param2) {
        MainPageHome fragment = new MainPageHome();
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
        View view=inflater.inflate(R.layout.fragment_main_page_home, container, false);
        return view;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        initView(view);
    }
    public View initView(View view){
        this.search_btn=view.findViewById(R.id.main_home_page_search_button);
        this.search_input=view.findViewById(R.id.main_home_page_search_input);
        this.search_type_spinner=view.findViewById(R.id.main_home_page_search_type_spinner);

        // 创建一个 ArrayAdapter
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, search_types);

        // 设置下拉列表的样式
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // 将适配器应用到 Spinner
        this.search_type_spinner.setAdapter(adapter);

        // 设置 Spinner 的选择事件监听器
        this.search_type_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getItemAtPosition(position).toString();
                Toast.makeText(getContext(), "现在搜索类型为" + selectedItem, Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // 如果没有选择任何项目时的处理
            }
        });
        return view;
    }
}