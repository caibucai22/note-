package cn.henu.cs.note.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public abstract class BaseActivity extends AppCompatActivity {
    public Context mContext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;

        setContentView(initLayout());
        initView();
        initData();
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

    public ProgressDialog crateProgressDialog(String Title,String Message,int Style) {
        ProgressDialog pd = new ProgressDialog(mContext);
        //设置进度条风格为圆形
        pd.setProgressStyle(Style);
        pd.setTitle(Title);
        pd.setMessage(Message);
        pd.setIndeterminate(false);//设置进度条是否不明确
        pd.setCancelable(false);
        return pd;
    }


}
