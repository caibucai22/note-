package cn.henu.cs.note.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import cn.henu.cs.note.R;

public class my_Fragment extends Fragment {

    private ListView listView1, listView2, listView3, listView4;

    public my_Fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_my_, container, false);
        return v;
    }

//    private void initView(View v) {
//        listView1 = v.findViewById(R.id.listView1);
//        listView2 = v.findViewById(R.id.listView2);
//        listView3 = v.findViewById(R.id.listView3);
//        listView4 = v.findViewById(R.id.listView4);
//
//        listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(getActivity(), "我的收藏",Toast.LENGTH_SHORT).show();
//            }
//        });
//
//    }
}