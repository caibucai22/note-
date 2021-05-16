package cn.henu.cs.note;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link my_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class my_Fragment extends Fragment {

    // 测试设置页面实现情况 实现button 后跳转
    private Button button;

    private ListView listView;
    //ListView所需的数据
    private String[] itemNames = {"我的收藏", "设置", "与我们联系", "退出登录"};
    private Integer[] itemIcons = {R.drawable.myloveicon, R.drawable.config, R.drawable.chet_with_us, R.drawable.myinterface_logout};

    private MyArrayDataAdapter adapter;
    private View contentView;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public my_Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment my_Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static my_Fragment newInstance(String param1, String param2) {
        my_Fragment fragment = new my_Fragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onStart() {
        demo();
        super.onStart();
    }

    private void demo() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        contentView = inflater.inflate(R.layout.fragment_my_, container, false);
        adapter = new MyArrayDataAdapter();
        listView = contentView.findViewById(R.id.my_fragment_myListView);
        //listView.setAdapter(adapter);
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                switch (position){
//                    case 0:break;
//                    case 1:break;
//                    case 2:break;
//                    case 3:break;
//                }
//            }
//        });
        return contentView;
    }

    //适配器
    class MyArrayDataAdapter extends BaseAdapter {

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
                convertView = getLayoutInflater().inflate(R.layout.fragment_my_, parent, false);
                //打开界面中的控件
                viewHolder = new MyViewHolder();
                viewHolder.itemName = convertView.findViewById(R.id.itemName);
                viewHolder.imageView = convertView.findViewById(R.id.itemIcon);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (MyViewHolder) convertView.getTag();
            }

//            viewHolder.itemName.setText(itemNames[position]);
//            viewHolder.imageView.setImageResource(itemIcons[position]);
            return convertView;
        }
    }
}