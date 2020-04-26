package com.example.my.myuipractice;

import android.content.Intent;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;
import com.yanzhenjie.permission.PermissionListener;
import com.yanzhenjie.permission.Rationale;
import com.yanzhenjie.permission.RationaleListener;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

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
    private Button btnWriteFile;
    private EditText etWriteContent;
    private final static String SD_PATH = "/storage/sdcard1/";
    private final static String TEST_FILE_NAME = "test_write.txt";

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
        etWriteContent = findViewById(R.id.et_write);
        btnWriteFile = findViewById(R.id.btn_write_file);
        btnWriteFile.setOnClickListener(new onClickBtn());
        findViewById(R.id.btn_screen).setOnClickListener(new onClickBtn());
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
                case R.id.btn_write_file:
                    String content = etWriteContent.getText().toString();
                    int res = writeTest(content);
                    if (res == 0) {
                        Toast.makeText(MainActivity.this, "写入成功", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(MainActivity.this, "写入失败 " + res, Toast.LENGTH_SHORT).show();
                    }
                    break;
                case R.id.btn_screen:
                    openAct(ScreenActivity.class);
                    break;
                default:
                    break;
            }
        }
    }

    private int writeTest(String content) {
        Log.d("zzc", "路径：" + Environment.getExternalStorageDirectory());
        Log.d("zzc", "路径：" + Environment.getDataDirectory());
        File file = new File(SD_PATH, TEST_FILE_NAME);
        if (file.exists()) {
            file.delete();
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
                return -1;
            }
        }
        try {
            FileWriter fw = new FileWriter(file);
            fw.write(content);
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
            return -2;
        }
        return 0;
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
