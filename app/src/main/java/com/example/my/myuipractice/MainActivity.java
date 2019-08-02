package com.example.my.myuipractice;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    /**
     * 自动完成文本
     */
    private Button btnAutoText;
    /**
     * 拖动条
     */
    private Button btnSeekBar;
    /**
     * 星级评分条
     */
    private Button btnRatingBar;
    /**
     * 底部弹出框
     */
    private Button btnPopupWindow;
    /**
     * 文件管理
     */
    private Button btnFileManagement;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    /**
     * 初始化函数，获取控件id,并给按钮添加监听
     */
    private void init() {
        btnAutoText = (Button) findViewById(R.id.btn_auto_text);
        btnAutoText.setOnClickListener(new onClickBtn());
        btnSeekBar = (Button) findViewById(R.id.btn_seek_bar);
        btnSeekBar.setOnClickListener(new onClickBtn());
        btnRatingBar = (Button) findViewById(R.id.btn_rating_bar);
        btnRatingBar.setOnClickListener(new onClickBtn());
        btnPopupWindow = (Button) findViewById(R.id.btn_popup_window);
        btnPopupWindow.setOnClickListener(new onClickBtn());
        btnFileManagement = (Button) findViewById(R.id.btn_file_management);
        btnFileManagement.setOnClickListener(new onClickBtn());
    }

    /**
     * 处理监听事件
     */
    class onClickBtn implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_auto_text:
                    openAct(AutoTextActivity.class);
                    break;
                case R.id.btn_seek_bar:
                    openAct(SeekBarActivity.class);
                    break;
                case R.id.btn_rating_bar:
                    openAct(RatingBarActivity.class);
                    break;
                case R.id.btn_popup_window:
                    openAct(PopupWindowActivity.class);
                    break;
                case R.id.btn_file_management:
                    openAct(FileManagementActivity.class);
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * 打开一个activity页面，不传递数据
     *
     * @param actClass 传入要跳转的activity
     */
    public void openAct(Class<?> actClass) {
        Intent intent = new Intent(this, actClass);
        startActivity(intent);
    }
}
