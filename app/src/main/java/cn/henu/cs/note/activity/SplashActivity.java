package cn.henu.cs.note.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import cn.henu.cs.note.R;

public class SplashActivity extends AppCompatActivity {
    private int DELAYED_TIME = 5;
    private TextView jumpText;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        jumpText = findViewById(R.id.jumpText);

        new Thread() {
            public void run() {
                while (DELAYED_TIME >= 0) {
                    try {
                        jumpText.setText("    剩余" + DELAYED_TIME + "秒跳转");
                        DELAYED_TIME--;
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                jumpActivity();
            }
        }.start();
    }


    public void jump(View view) {
        DELAYED_TIME = -1;
    }

    public void jumpActivity() {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        if (sharedPreferences.getBoolean("isLogin", false)) {
            startActivity(new Intent(this, MainActivity.class));
        } else {
            startActivity(new Intent(this, LoginActivity.class));
        }
        String TAG = "jumpActivity";
        Log.e(TAG, "jumpActivity: 执行了跳转界面");
        finish();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            Intent intent = new Intent();
            intent.setAction("NIGHT_SWITCH");
            sendBroadcast(intent);
            finish();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }
}