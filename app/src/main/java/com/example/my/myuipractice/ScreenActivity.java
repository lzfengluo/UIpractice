package com.example.my.myuipractice;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.admin.DeviceAdminReceiver;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.PowerManager;
import android.view.View;
import android.widget.Toast;

public class ScreenActivity extends Activity implements View.OnClickListener {
    private DevicePolicyManager devicePolicyManager = null;
    private ComponentName screenReceiver = null;
    private static final int SCREEN_OFF = 0;
    private static final int SCREEN_ON = 1;
    private PowerManager powerManager = null;
    private PowerManager.WakeLock wakeLock;
    private static int TIME = 10000;
    private volatile boolean isStop = false;

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (isStop) {
                return;
            }
            switch (msg.what) {
                case SCREEN_OFF:
                    turnOffScreen();
                    handler.sendEmptyMessageDelayed(SCREEN_ON, TIME);
                    break;
                case SCREEN_ON:
                    turnOnScreen();
                    handler.sendEmptyMessageDelayed(SCREEN_OFF, TIME);
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen);
        initView();
        screenReceiver = new ComponentName(ScreenActivity.this, ScreenOnAndOffReceiver.class);
        powerManager = (PowerManager) getSystemService(POWER_SERVICE);
        devicePolicyManager = (DevicePolicyManager) getSystemService(Context.DEVICE_POLICY_SERVICE);

        Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
        intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, screenReceiver);
        intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION, "使用亮灭屏功能");

        startActivityForResult(intent, 0);
    }

    private void initView() {
        findViewById(R.id.btn_10s).setOnClickListener(this);
        findViewById(R.id.btn_60s).setOnClickListener(this);
        findViewById(R.id.btn_600s).setOnClickListener(this);
        findViewById(R.id.btn_stop).setOnClickListener(this);
    }

    private void turnOnScreen() {
        wakeLock = powerManager.newWakeLock(PowerManager.SCREEN_BRIGHT_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP, ScreenActivity.class.getSimpleName());
        wakeLock.acquire();
        wakeLock.release();
    }

    private void turnOffScreen() {
        devicePolicyManager.lockNow();
    }

    private void start() {
        boolean hasPermission = devicePolicyManager.isAdminActive(screenReceiver);
        if (hasPermission) {
            turnOffScreen();
            handler.sendEmptyMessageDelayed(SCREEN_ON, TIME);
        } else {
            Toast.makeText(ScreenActivity.this, "无设备管理权限", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_10s:
                isStop = false;
                TIME = 10 * 1000;
                start();
                break;
            case R.id.btn_60s:
                isStop = false;
                TIME = 60 * 1000;
                start();
                break;
            case R.id.btn_600s:
                isStop = false;
                TIME = 10 * 60 * 1000;
                start();
                break;
            case R.id.btn_stop:
                isStop = true;
                break;
            default:
                break;
        }
    }

    public class ScreenOnAndOffReceiver extends DeviceAdminReceiver {
        public ScreenOnAndOffReceiver() {

        }

        private void showToast(Context context, String msg) {
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onEnabled(Context context, Intent intent) {
            showToast(context,
                    "设备管理器启用");
        }

        @Override
        public void onDisabled(Context context, Intent intent) {
            showToast(context,
                    "设备管理器禁用");
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        isOpen();
    }


    private void isOpen() {
        if (devicePolicyManager.isAdminActive(screenReceiver)) {

            Toast.makeText(ScreenActivity.this, "设备已被激活",
                    Toast.LENGTH_LONG).show();

        } else {

            Toast.makeText(ScreenActivity.this, "设备没有被激活",
                    Toast.LENGTH_LONG).show();

        }
    }

    @Override
    protected void onDestroy() {
        isStop = true;
        super.onDestroy();
    }

}
