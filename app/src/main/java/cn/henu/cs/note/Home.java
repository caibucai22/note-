package cn.henu.cs.note;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Home extends AppCompatActivity {
    //创建菜单，加载home_menu.xml布局文件
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_menu,menu);
        return true;
    }

    //当OptionsMenu被选中的时候处理具体的响应事件
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.refresh:
                Toast.makeText(this,"Option 1",Toast.LENGTH_SHORT).show();
                return true;
            case R.id.showAbstract:
                Toast.makeText(this,"Option 2", Toast.LENGTH_SHORT).show();
                return true;
            default:
                //do nothing
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
    }
}
