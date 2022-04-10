package cn.henu.cs.note.activity;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import cn.bmob.v3.Bmob;
import cn.henu.cs.note.R;

public abstract class BaseActivity extends AppCompatActivity {
    public Context mContext;
    protected BroadcastReceiver receiver;
    public final String ACTION = "NIGHT_SWITCH";
    protected IntentFilter filter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bmob.initialize(this, "53c2ee7edfe3b609d97de4d350772ed6");

        setNightMode();

        mContext = this;


        filter = new IntentFilter();
        filter.addAction(ACTION);

        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Log.d("TAG", "onReceive: needRefresh");
                needRefresh();
            }
        };
        registerReceiver(receiver, filter);

    }

    protected abstract int initLayout();

    protected abstract void initView();

    protected abstract void initData();

    public void showToast(String msg) {
        Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
    }

    public void navigateTo(Class cls) {
        Intent intent = new Intent(mContext, cls);
        startActivity(intent);
    }

    public ProgressDialog crateProgressDialog(String Title, String Message, int Style) {
        ProgressDialog pd = new ProgressDialog(mContext);
        //设置进度条风格为圆形
        pd.setProgressStyle(Style);
        pd.setTitle(Title);
        pd.setMessage(Message);
        pd.setIndeterminate(false);//设置进度条是否不明确
        pd.setCancelable(false);
        return pd;
    }

    public boolean isNightMode() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        return sharedPreferences.getBoolean("nightMode", false);
    }

    public void setNightMode() {
        if (isNightMode()) {
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
            this.setTheme(R.style.NightTheme);
        } else this.setTheme(R.style.DayTheme);
        Log.e("TAG", "我是baseActivity: " + isNightMode());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }

    protected abstract void needRefresh();
}
