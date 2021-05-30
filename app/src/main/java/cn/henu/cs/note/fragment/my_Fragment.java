package cn.henu.cs.note.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import cn.henu.cs.note.R;
import cn.henu.cs.note.Settings;
import cn.henu.cs.note.activity.SettingsActivity;

public class my_Fragment extends Fragment {

    private ListView listView1, listView2,  listView4;  //listView3
    private LinearLayout listView3;
    private Context context;
    public my_Fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_my_, container, false);
        context = getActivity();
        listView3 = v.findViewById(R.id.listView3);
        listView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, SettingsActivity.class);
                my_Fragment.this.startActivity(intent);
            }
        });

        return v;
    }

}