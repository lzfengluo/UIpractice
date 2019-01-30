package com.example.my.myuipractice;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

public class AutoTextActivity extends Activity {

    private AutoCompleteTextView autoText;
    private Button searchBtn;

    private static final String[] COUNTRIES = new String[]{
            "明日科技",
            "明日之后",
            "明日之子",
            "明日科技有限公司",
            "河北省明日出版社",
            "明日",
            "ming ri",
            "ming ri zhi hou",
            "ming ri zhi zi",
            "he bei sheng ming ri chu ban she "
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auto_text);
        init();
    }

    private void init(){
        autoText = (AutoCompleteTextView)findViewById(R.id.auto_atv);
        searchBtn = (Button) findViewById(R.id.auto_search_btn);
        // 为自动完成文本框设置适配器
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,COUNTRIES);
        autoText.setAdapter(adapter);
        // 为“搜索”按钮添加单击监听事件
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AutoTextActivity.this,autoText.getText().toString(),Toast.LENGTH_SHORT).show();
            }
        });
    }
}
