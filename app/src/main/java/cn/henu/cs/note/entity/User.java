package cn.henu.cs.note.entity;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.LogInListener;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SaveListener;

import android.view.View;

import com.google.android.material.snackbar.Snackbar;

public class User extends BmobUser {

    private String signature;

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public User() {
    }

    //封装保存方法 未实现
//    public void save(String username,String password,LoginActivity ){

    //封装保存方法 未实现
//    public void save(String username,String password,LoginActivity){
//        User admin = new User(username,password);
////        admin.setId(1);
//        admin.setUsername(username);
//        admin.setPassword(password);
//        admin.save(new SaveListener<String>() {
//            @Override
//            public void done(String objectId, BmobException e) {
//                if(e==null){
////                            System.out.println("添加数据成功，返回objectId为："+objectId);
////                    Toast.makeText(RegisterActivity.this,"注册成功",Toast.LENGTH_LONG).show();
//                }else{
////                            System.out.println("创建数据失败：" + e.getMessage());
////                    Toast.makeText(RegisterActivity.this,"注册失败：" ,Toast.LENGTH_LONG).show();
//                }
//            }
//        });
//    }

    public boolean queryData(String username, String password) {
        boolean flag = false;
        BmobQuery<User> query = new BmobQuery<User>();

        query.getObject(username, new QueryListener<User>() {
            @Override
            public void done(User user, BmobException e) {
                user.getUsername();
//                user.getPassword();
//                if (username.equals(user.getUsername()) && password.equals(user.getPassword())) {
//
//                }
            }
        });

        return flag;
    }

    /**
     * 账号密码登录
     */
    public boolean login(final View view, String username, String password) {
        final boolean isL = false;
        final BmobUser user = new BmobUser();
        //此处替换为你的用户名
        user.setUsername(username);
        //此处替换为你的密码
        user.setPassword(password);
        user.login(new SaveListener<User>() {
            @Override
            public void done(User bmobUser, BmobException e) {
                if (e == null) {
                    User user = BmobUser.getCurrentUser(User.class);

                    Snackbar.make(view, "登录成功：" + user.getUsername(), Snackbar.LENGTH_LONG).show();
                } else {
                    Snackbar.make(view, "登录失败：" + e.getMessage(), Snackbar.LENGTH_LONG).show();
                }
            }
        });
        return false;
    }


    public void loginByAccount(final View view, String username, String password) {
        //此处替换为你的用户名密码
        BmobUser.loginByAccount(username, password, new LogInListener<User>() {
            @Override
            public void done(User user, BmobException e) {
                if (e == null) {
                    Snackbar.make(view, "登录成功：" + user.getUsername(), Snackbar.LENGTH_LONG).show();
                } else {
                    Snackbar.make(view, "登录失败：" + e.getMessage(), Snackbar.LENGTH_LONG).show();
                }
            }
        });
    }

    /**
     * 账号密码注册
     */
    private void signUp(final View view) {
        final BmobUser user = new BmobUser();
        user.setUsername("" + System.currentTimeMillis());
        user.setPassword("" + System.currentTimeMillis());
        user.signUp(new SaveListener<User>() {
            @Override
            public void done(User user, BmobException e) {
                if (e == null) {
                    Snackbar.make(view, "注册成功", Snackbar.LENGTH_LONG).show();
                } else {
                    Snackbar.make(view, "尚未失败：" + e.getMessage(), Snackbar.LENGTH_LONG).show();
                }
            }
        });
    }
}

