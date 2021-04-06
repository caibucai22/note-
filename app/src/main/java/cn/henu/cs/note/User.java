package cn.henu.cs.note;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobObject;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

import android.widget.Toast;

public class User extends BmobObject {
    private int id;
    private String username;
    private String password;

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
}
