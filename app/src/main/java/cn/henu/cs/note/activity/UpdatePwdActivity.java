package cn.henu.cs.note.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.Preference;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.InputStream;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;
import cn.henu.cs.note.R;
import cn.henu.cs.note.entity.User;

import static cn.henu.cs.note.utils.GraphProcess.toRoundBitmap;

public class UpdatePwdActivity extends AppCompatActivity {
    private TextView userName;
    private ImageView header;
    private EditText oldPwd, newPwd;
    private Button cancel, confirm;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private Context context = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //默认初始化
        Bmob.initialize(this, "53c2ee7edfe3b609d97de4d350772ed6");
        setContentView(R.layout.activity_update_pwd);
        context = getApplicationContext();
        sharedPreferences = getSharedPreferences("globaldata", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        //设置头像下面的用户名
//        userName.setText(sharedPreferences.getString("username", ""));

        //改为圆形头像
        header = findViewById(R.id.header);
        Resources r = this.getResources();
        @SuppressLint("ResourceType") InputStream is = r.openRawResource(R.drawable.header);
        BitmapDrawable bmpDraw = new BitmapDrawable(is);
        Bitmap bmp = bmpDraw.getBitmap();
        // 将图片转换成圆形图片
        Bitmap bm = toRoundBitmap(bmp);
        //传给imagview进行显示
        header.setImageBitmap(bm);

        //更改密码
        oldPwd = findViewById(R.id.oldPwd);
        newPwd = findViewById(R.id.newPwd);

//        String old = oldPwd.getText().toString();
//        String latest = newPwd.getText().toString();
        confirm = findViewById(R.id.confirmUpdate);
//        confirm.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                BmobUser.updateCurrentUserPassword(old, latest, new UpdateListener() {
//                    @Override
//                    public void done(BmobException e) {
//                        if (e == null) {
//                            //保存新的密码到 sharedPreferences latest 或者 更新原来存储的password
//                            // 逻辑是 判断输入的旧密码是否正确，正确的话就修改，不正确的话就重新修改、或者退出
//                            editor.putString("password", latest).commit();
//                            Log.e("TAG", "done: 修改成功");
//                            Toast.makeText(UpdatePwdActivity.this, "修改成功", Toast.LENGTH_LONG).show();
//                        } else {
//                            Log.e("TAG", "错误信息"+e.getMessage());
//                            Toast.makeText(UpdatePwdActivity.this, "修改失败：" + e.getMessage(), Toast.LENGTH_LONG).show();
//                        }
//                    }
//                });
//            }
//        });
        cancel = findViewById(R.id.cancelUpdate);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }

    public void test(View view) {
        String old = oldPwd.getText().toString();
        String latest = newPwd.getText().toString();
        Toast.makeText(context, "dianji", Toast.LENGTH_SHORT).show();
        BmobUser.updateCurrentUserPassword(old, latest, new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    //保存新的密码到 sharedPreferences latest 或者 更新原来存储的password
                    // 逻辑是 判断输入的旧密码是否正确，正确的话就修改，不正确的话就重新修改、或者退出
                    editor.putString("password", latest).commit();
                    Log.e("TAG", "done: 修改成功");
                    Toast.makeText(UpdatePwdActivity.this, "修改成功", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(context,LoginActivity.class);
                    startActivity(intent);
                } else {
                    Log.e("TAG", "错误信息" + e.getMessage());
                    Toast.makeText(UpdatePwdActivity.this, "修改失败：" + e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
