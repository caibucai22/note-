package cn.henu.cs.note;

import androidx.appcompat.app.AppCompatActivity;

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

import com.google.android.material.snackbar.Snackbar;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class Login extends AppCompatActivity {

    private EditText userName, userPwd;
    private Button loginBut, registerBut;
    private ImageButton pwdImageBut;
    private CheckBox rememberCheckBox;
    private SharedPreferences pref;//用于记住密码
    private SharedPreferences.Editor pwdeditor;//用于记住密码
    private String userNameS, userPwdS;//用于验证用户名和密码

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //默认初始化
        Bmob.initialize(this, "53c2ee7edfe3b609d97de4d350772ed6");
        /**
         * 默认初始化
         * 有更加细致的初始化待选项，以后再进行添加
         */

        //1.获取各组件ID
        userName = findViewById(R.id.UserID);
        userPwd = findViewById(R.id.UserPwd);
        loginBut = findViewById(R.id.loginBut);
        registerBut = findViewById(R.id.registerBut);
        pwdImageBut = findViewById(R.id.pwdShowBut);
        rememberCheckBox = findViewById(R.id.rememberCheckBox);

//        //设置记住密码功能
        //未能实现
//        pref = PreferenceManager.getDefaultSharedPreferences(this);
//        //boolean isPwdRemember = getSharedPreferences("remember_password",MODE_PRIVATE).getBoolean(false);
//        boolean isPwdRemember = pref.getBoolean("remember_password",false);
//        if(isPwdRemember){
//            userName.setText(pref.getString("userName", ""));
//            userPwd.setText(pref.getString("userPwd", ""));
//            rememberCheckBox.setChecked(true);
//        }
        //2.注册小眼睛（密码显示）监听器
        pwdImageBut.setOnClickListener(new View.OnClickListener() {
            boolean iscilck = true;//按钮状态

            @Override
            public void onClick(View v) {
                if (iscilck) {
                    iscilck = false;
                    userPwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    pwdImageBut.setBackgroundResource(R.drawable.pwdshow);
                } else {
                    iscilck = true;
                    userPwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    pwdImageBut.setBackgroundResource(R.drawable.pwdunshow);
                }

            }
        });

        //3.注册登录按钮监听器
        loginBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //获得用户输入的用户名或者密码
                userNameS = userName.getText().toString();
                userPwdS = userPwd.getText().toString();



                if (userNameS.equals("admin") && userPwdS.equals("admin")) {//密码和用户名都不正确
                    //记住密码
                    //未能实现
//                    pwdeditor.putBoolean("remember_password", true);
//                    if(rememberCheckBox.isChecked()){
//                        pwdeditor.putBoolean("remember_password",true);
//                        pwdeditor.putString("userName",userNameS);
//                        pwdeditor.putString("userPwd",userPwdS);
//                    }else{
//                        pwdeditor.clear();
//                    }
//                    pwdeditor.apply();
                    //实例化主界面
                    Intent it = new Intent(Login.this, Home.class);
                    //启动主界面
                    Login.this.startActivityForResult(it, 1);

                    //从服务器数据库获取数据进行验证登录
//                    User loginer = new User();

                    BmobUser loginer = new BmobUser();
                    loginer.setUsername(userNameS);
                    loginer.setPassword(userPwdS);
                    loginer.login(new SaveListener<BmobUser>() {
                    @Override
                    public void done(BmobUser bmobUser, BmobException e) {
                        if (e == null) {
                            BmobUser user = BmobUser.getCurrentUser(User.class);
                            Toast.makeText(Login.this, "登录成功：" + user.getUsername(), Toast.LENGTH_LONG).show();
                            //实例化主界面
                            Intent it = new Intent(Login.this, Home.class);
                            //启动主界面
                            Login.this.startActivityForResult(it, 1);
                        } else {
                            System.out.println(e.getMessage());
                            Toast.makeText(Login.this, "登录失败：" + e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });

                } else if (userNameS.equals("") || userPwdS.equals("")) {//用户名或密码为空
                    Toast.makeText(Login.this, "用户名/密码不能为空!", Toast.LENGTH_SHORT).show();
                } else {//密码或用户名错误
                    Toast.makeText(Login.this, "登录失败，密码或用户名错误!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        //4.注册 注册按钮监听器
        registerBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //实例化注册界面
                Intent it = new Intent(Login.this, Register.class);
                //启动注册
                Login.this.startActivityForResult(it, 1);

                //用户信息保存到服务器

            }
        });
    }
}