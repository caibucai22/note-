package cn.henu.cs.note.activity;

import android.app.ProgressDialog;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.henu.cs.note.R;
import cn.henu.cs.note.entity.User;

public class RegisterActivity extends BaseActivity {

    private Button regBut, regCancel;
    private ImageView regPwdShowBut1, regPwdShowBut2;
    private EditText userName, userPwd1, userPwd2;//userPwd1是用户输入的密码，  userPwd2是确认密码
    private EditText phoneNum, securityCode;
    private Boolean isSelected = false;
    String uname, pwd1, pwd2, pnum, scode;//用于获取用户输入
    String popInfor = "";//提示信息
    String trueSCode;//真正的验证码

    @Override
    protected int initLayout() {
        return R.layout.register;
    }

    @Override
    protected void initView() {
        //默认初始化
        Bmob.initialize(this, "53c2ee7edfe3b609d97de4d350772ed6");

        //1.获取各组件ID
        regBut = findViewById(R.id.regBut);
        regCancel = findViewById(R.id.regCancel);
        regPwdShowBut1 = findViewById(R.id.regPwdShowBut1);
        regPwdShowBut2 = findViewById(R.id.regPwdShowBut2);
        userName = findViewById(R.id.regUserName);
        userPwd1 = findViewById(R.id.regUserPwd1);
        userPwd2 = findViewById(R.id.regUserPwd2);
        phoneNum = findViewById(R.id.regPhoneNum);
        securityCode = findViewById(R.id.securityCode);
    }

    @Override
    protected void initData() {
        //2.注册小眼睛（密码显示）监听器
        regPwdShowBut1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isSelected) {
                    isSelected = false;
                    userPwd1.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    isSelected = true;
                    userPwd1.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
                regPwdShowBut1.setSelected(!isSelected);
            }
        });
        regPwdShowBut2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isSelected) {
                    isSelected = false;
                    userPwd2.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    isSelected = true;
                    userPwd2.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
                regPwdShowBut2.setSelected(!isSelected);
            }
        });

        //3.注册按钮监听器
        regBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkUserName();
                checkPwd();
                if (checkUserName() && checkPwd()) {

                    //用户信息保存到数据库  成功实现
                    User signuser = new User();
                    signuser.setUsername(userName.getText().toString());
                    signuser.setPassword(userPwd1.getText().toString());
                    ProgressDialog pd = new ProgressDialog(RegisterActivity.this, ProgressDialog.STYLE_SPINNER);
                    pd.setTitle("注册");
                    pd.setMessage("正在注册请稍等...");
                    pd.setIndeterminate(false);
                    pd.setCancelable(false);
                    pd.show();
                    signuser.signUp(new SaveListener<User>() {
                        @Override
                        public void done(User user, BmobException e) {
                            if (e == null) {
                                pd.dismiss();
                                showToast("注册成功");
                            } else {
                                pd.dismiss();
                                showToast("注册失败! 错误代码:"+ e.getErrorCode());
                            }
                        }
                    });
                    //注册成功跳转回登陆界面
                    navigateTo(LoginActivity.class);
                    finish();
                } else {
                    popInfor.replace(" ", "");
                    showToast(popInfor);
                }
                popInfor = "";
            }
        });
        //4.退出按钮监听器
        regCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void needRefresh() {

    }

    //判断用户名是否标准
    private boolean checkUserName() {
        //获取用户输入的用户名
        uname = userName.getText().toString();
        //1.用户名为空
        if (uname.equals("")) {
            popInfor += "用户名不能为空！！！\n";
            return false;
        }
        //2.用户名长度过长
        if (uname.length() > 10) {
            popInfor += "用户名过长！！！\n";
            return false;
        }
        //3.用户名格式正确
        return true;
    }

    //判断密码是否标准
    private boolean checkPwd() {
        boolean flag = true;
        //获取用户输入的密码
        pwd1 = userPwd1.getText().toString();
        pwd2 = userPwd2.getText().toString();
        //1.第一个密码为空
        if (pwd1.equals("")) {
            popInfor += "密码不能为空！！！\n";
            flag = false;
        }
        //2.密码过长
        if (pwd1.length() > 12) {
            popInfor += "密码太长！！！\n";
            flag = false;
        }
        //3.密码过短
        if (pwd1.length() < 6) {
            popInfor += "密码太短！！！\n";
            flag = false;
        }
        //4.两次输入密码不一致
        if (!pwd1.equals(pwd2)) {
            popInfor += "两次输入的密码不一致！！！\n";
            flag = false;
        }
        return flag;
    }

}
