package cn.henu.cs.note;

import android.app.ListActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Settings extends ListActivity {
    private final String[] items = {"账号安全", "深色模式", "清除缓存", "检查更新"};
    private ListView listView;

    public void OnCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.settings);
        this.listView = (ListView) super.findViewById(R.id.settings_listview);

        SimpleAdapter adapter = new SimpleAdapter(this, getData(), R.layout.setttings_items,
                new String[]{"skill", "picture"}, new int[]{R.id.skill, R.id.picture});

        listView.setAdapter(adapter);

    }

    private List<Map<String, Object>> getData() {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < items.length; i++) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("skill", items[i]);
            map.put("picture", R.drawable.right);
            list.add(map);
        }

        return list;
    }
}
