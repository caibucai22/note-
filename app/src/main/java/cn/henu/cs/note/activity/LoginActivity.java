package cn.henu.cs.note.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.henu.cs.note.R;

public class LoginActivity extends BaseActivity {

    private EditText userName, userPwd;
    private Button loginBut, registerBut;
    private ImageButton pwdImageBut;
    private CheckBox rememberCheckBox;
    private SharedPreferences pref;//用于记住密码
    private SharedPreferences.Editor pwdeditor;//用于记住密码
    private String userNameS, userPwdS;//用于验证用户名和密码
    private boolean isSelected=false;

    @Override
    protected int initLayout() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView() {
        //1.获取各组件ID
        userName = findViewById(R.id.UserID);
        userPwd = findViewById(R.id.UserPwd);
        loginBut = findViewById(R.id.loginBut);
        registerBut = findViewById(R.id.registerBut);
        pwdImageBut = findViewById(R.id.pwdShowBut);
        rememberCheckBox = findViewById(R.id.rememberCheckBox);
    }

    @Override
    protected void initData() {
        //默认初始化
        Bmob.initialize(this, "53c2ee7edfe3b609d97de4d350772ed6");
        /**
         * 默认初始化
         * 有更加细致的初始化待选项，以后再进行添加
         */


        //2.注册小眼睛（密码显示）监听器
        pwdImageBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isSelected) {
                    isSelected = false;
                    userPwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    isSelected = true;
                    userPwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
                pwdImageBut.setSelected(!isSelected);
            }
        });

        //3.注册登录按钮监听器
        loginBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //获得用户输入的用户名或者密码
                userNameS = userName.getText().toString();
                userPwdS = userPwd.getText().toString();

                BmobUser userlogin = new BmobUser();
                userlogin.setUsername(userNameS);
                userlogin.setPassword(userPwdS);
                userlogin.login(new SaveListener<BmobUser>() {
                    @Override
                    public void done(BmobUser bmobUser, BmobException e) {
                        if (e == null) {
//                            //实例化主界面
//                            Intent it = new Intent(LoginActivity.this, HomeActivity.class);
//                            //启动主界面
//                            LoginActivity.this.startActivityForResult(it, 1);
                            navigateTo(HomeActivity.class);
                            showToast(bmobUser.getUsername() + "登陆成功");
                            //Toast.makeText(LoginActivity.this, bmobUser.getUsername() + "登陆成功", Toast.LENGTH_LONG).show();
                        } else {
                            showToast("登陆失败" + e.getMessage());
                        }
                    }
                });
            }
        });
        //4.注册 注册按钮监听器
        registerBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateTo(RegisterActivity.class);
            }
        });
    }
}