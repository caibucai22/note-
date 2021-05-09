package cn.henu.cs.note;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MyInterface extends AppCompatActivity {
    private ListView listView;
    private String[] itemNames = {"我的收藏", "与我们联系", "设置", "退出登录"};
    private Integer[] itemIcons = {R.drawable.myloveicon, R.drawable.chet_with_us, R.drawable.config, R.drawable.myinterface_logout};

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myinterface);

        listView = findViewById(R.id.myListView);
        listView.setAdapter(new MyArrayDataAdapter(MyInterface.this));
    }

    class MyArrayDataAdapter extends BaseAdapter {
        private Context mContext;
        private LayoutInflater mLayoutInflater;

        public MyArrayDataAdapter(Context mContext) {
            this.mContext = mContext;
            mLayoutInflater = LayoutInflater.from(mContext);
        }

        @Override
        public int getCount() {
            return itemNames.length;
        }

        @Override
        public Object getItem(int position) {
            return itemNames[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        //定义一个ViewHolder
        class MyViewHolder {
            TextView itemName;
            ImageView imageView;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            MyViewHolder viewHolder;

            if (convertView == null) {
                //将布局文件转换成view对象
                convertView = View.inflate(MyInterface.this, R.layout.setup_list_item, null);
                //打开界面中的控件
                viewHolder = new MyViewHolder();
                viewHolder.itemName = convertView.findViewById(R.id.itemName);
                viewHolder.imageView = convertView.findViewById(R.id.itemIcon);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (MyViewHolder) convertView.getTag();
            }
            viewHolder.itemName.setText(itemNames[position]);
            viewHolder.imageView.setImageResource(itemIcons[position]);
            return convertView;
        }
    }
}
