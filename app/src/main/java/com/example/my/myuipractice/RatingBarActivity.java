package com.example.my.myuipractice;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

public class RatingBarActivity extends Activity {

    private RatingBar ratingBar;
    private Button submitBtn;
    private TextView ratingBarText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating_bar);
        init();
    }

    private void init(){
        ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        submitBtn = (Button) findViewById(R.id.ratingBar_btn);
        ratingBarText = (TextView) findViewById(R.id.ratingBar_tv);
        // 给按钮添加监听
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int result = ratingBar.getProgress();//获取进度
                float rating = ratingBar.getRating();//获取等级
                float step = ratingBar.getStepSize();//获取每次要改变多少个星级
                ratingBarText.setText("当前分数："+rating+"\n进度："+result+"\n等级："+rating+"\n每次改变："+step+"个星级");
            }
        });
    }
}
