package cn.henu.cs.note.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.LogInListener;
import cn.bmob.v3.listener.SaveListener;
import cn.henu.cs.note.R;
import cn.henu.cs.note.entity.User;

public class LoginActivity extends AppCompatActivity {

    private EditText userName, userPwd;
    private Button loginBut, registerBut;
    private ImageButton pwdImageBut;
    private CheckBox rememberCheckBox;
    private boolean showPassword = false;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bmob.initialize(this, "53c2ee7edfe3b609d97de4d350772ed6");
        setContentView(R.layout.login_layout);
        context = this;
        initView();
        initData();
    }


    protected void initView() {
        //1.获取各组件ID
        userName = findViewById(R.id.UserID);
        userPwd = findViewById(R.id.UserPwd);
        loginBut = findViewById(R.id.loginBut);
        registerBut = findViewById(R.id.registerBut);
        pwdImageBut = findViewById(R.id.pwdShowBut);
        rememberCheckBox = findViewById(R.id.rememberCheckBox);
    }

    protected void initData() {

        //2.注册小眼睛（密码显示）监听器
        pwdImageBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPassword = !showPassword;
                pwdImageBut.setSelected(showPassword);
                if (showPassword) {
                    userPwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    userPwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });
        //3.记住密码
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        boolean isRemember = sharedPreferences.getBoolean("isRemember", false);
        if (isRemember) {
            userName.setText(sharedPreferences.getString("username", ""));
            userPwd.setText(sharedPreferences.getString("password", ""));
            rememberCheckBox.setChecked(true);
        }
        //4.注册登录按钮监听器
        loginBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
        //5.注册 注册按钮监听器
        registerBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });
    }

    void login() {
        String userAccount = userName.getText().toString().trim();
        String userPassword = userPwd.getText().toString().trim();

        if (userAccount.isEmpty()) {
            Toast.makeText(context, "请输入用户账号", Toast.LENGTH_SHORT).show();
            return;
        }

        if (userPassword.isEmpty()) {
            Toast.makeText(context, "请输入用户密码", Toast.LENGTH_SHORT).show();
            return;
        }
        ProgressDialog pd = new ProgressDialog(context, ProgressDialog.STYLE_SPINNER);
        pd.setTitle("登录");
        pd.setMessage("正在登录请稍等...");
        pd.setIndeterminate(false);
        pd.setCancelable(false);
        pd.show();
        BmobUser.loginByAccount(userAccount, userPassword, new LogInListener<User>() {
            @Override
            public void done(User user, BmobException e) {

                if (e == null) {
                    editor = sharedPreferences.edit();
                    if (rememberCheckBox.isChecked()) {
                        editor.putBoolean("isRemember", true);
                        editor.putString("username", userAccount);
                        editor.putString("password", userPassword);
                    } else {
                        editor.clear();
                    }
                    editor.putBoolean("isLogin", true);
                    editor.commit();
                    pd.dismiss();
                    startActivity(new Intent(context, MainActivity.class));
                    Toast.makeText(context, user.getUsername() + "登陆成功", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    pd.dismiss();
                    Toast.makeText(context, e.getErrorCode() + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}