package cn.henu.cs.note;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyBaseAdapter extends BaseAdapter {

    private List<Map<String,Object>> data;
    private Context mcontext;

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ListView listView = new ListView(mcontext);
        return listView;
    }

    public MyBaseAdapter(Context mContext,List<Map<String,Object>> data){
        super();
        this.data = data;
        this.mcontext = mContext;
    }
}
