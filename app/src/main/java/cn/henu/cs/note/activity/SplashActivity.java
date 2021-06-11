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
    Thread thread;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        jumpText = findViewById(R.id.jumpText);

        thread = new Thread(new Runnable() {
            @Override
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
        });
        thread.start();
    }


    public void jump(View view) {
        thread.interrupt();
        jumpActivity();
    }

    public void jumpActivity() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        if (sharedPreferences.getBoolean("isLogin", false)) {
            startActivity(new Intent(this, MainActivity.class));
        } else {
            startActivity(new Intent(this, LoginActivity.class));
        }
        finish();
    }

}