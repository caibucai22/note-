package cn.henu.cs.note.activity;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.TextView;


import cn.henu.cs.note.R;

public class SplashActivity extends BaseActivity {
    private int DELAYED_TIME = 5;
    private TextView jumpText;
    private SharedPreferences sharedPreferences;


    @Override
    protected int initLayout() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initView() {
        jumpText = findViewById(R.id.jumpText);
    }

    @Override
    protected void initData() {
        new Thread(){
            public void run() {
                while (DELAYED_TIME>=0){
                    try {
                        jumpText.setText("    剩余"+DELAYED_TIME+"秒跳转");
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
            navigateTo(HomeActivity.class);
        } else {
            navigateTo(LoginActivity.class);
        }
        String TAG = "jumpActivity";
        Log.e(TAG, "jumpActivity: 执行了跳转界面");
        finish();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}