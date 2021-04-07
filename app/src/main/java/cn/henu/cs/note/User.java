package cn.henu.cs.note;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.LogInListener;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SaveListener;

import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;

public class User extends BmobUser {
    private int id;
    private String username;
    private String password;
    private String phonenum;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhonenum() {
        return phonenum;
    }

    public void setPhonenum(String phonenum) {
        this.phonenum = phonenum;
    }

    public User() {
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public User(int id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    //封装保存方法 未实现
//    public void save(String username,String password,Login){
//        User admin = new User(username,password);
////        admin.setId(1);
//        admin.setUsername(username);
//        admin.setPassword(password);
//        admin.save(new SaveListener<String>() {
//            @Override
//            public void done(String objectId, BmobException e) {
//                if(e==null){
////                            System.out.println("添加数据成功，返回objectId为："+objectId);
//                    Toast.makeText(,"注册成功",Toast.LENGTH_LONG).show();
//                }else{
////                            System.out.println("创建数据失败：" + e.getMessage());
//                    Toast.makeText(,"注册失败：" ,Toast.LENGTH_LONG).show();
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
                user.getPassword();
            }
        });


        return flag;
    }

    /**
     * 账号密码登录
     */
    public void login(final View view, String username, String password) {
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

