package cn.henu.cs.note.activity;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.henu.cs.note.R;
import cn.henu.cs.note.entity.User;

public class LoginActivity extends BaseActivity {

    private EditText userName, userPwd;
    private Button loginBut, registerBut;
    private ImageButton pwdImageBut;
    private CheckBox rememberCheckBox;
    private String userNameS, userPwdS;//用于验证用户名和密码
    private boolean showPassword = false;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    @Override
    protected int initLayout() {
        return R.layout.login_layout;
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
                if (showPassword) {
                    showPassword = false;
                    userPwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    showPassword = true;
                    userPwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
                pwdImageBut.setSelected(!showPassword);
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
                //获得用户输入的用户名或者密码
                userNameS = userName.getText().toString();
                userPwdS = userPwd.getText().toString();

                final User userLogin = new User();
                userLogin.setUsername(userNameS);
                userLogin.setPassword(userPwdS);
                ProgressDialog pd = new ProgressDialog(LoginActivity.this, ProgressDialog.STYLE_SPINNER);
                pd.setTitle("登录");
                pd.setMessage("正在登录请稍等...");
                pd.setIndeterminate(false);
                pd.setCancelable(false);
                pd.show();
                userLogin.login(new SaveListener<User>() {
                    @Override
                    public void done(User user, BmobException e) {
                        if (e == null) {
                            //用户输入的账号密码正确  记住密码
                            editor = sharedPreferences.edit();
                            if (rememberCheckBox.isChecked()) {
                                editor.putBoolean("isRemember", true);
                                editor.putString("username", userNameS);
                                editor.putString("password", userPwdS);
                            } else {
                                editor.clear();
                            }
                            editor.putBoolean("isLogin", true);
                            editor.commit();
                            pd.dismiss();
                            navigateTo(MainActivity.class);
                            showToast(user.getUsername() + "登陆成功");
                            finish();
                        } else {
                            pd.dismiss();
                            showToast("登陆失败! 错误代码:" + e.getErrorCode());
                        }
                    }
                });
            }
        });
        //5.注册 注册按钮监听器
        registerBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateTo(RegisterActivity.class);
            }
        });
    }

    @Override
    protected void needRefresh() {

    }
}