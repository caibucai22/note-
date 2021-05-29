package cn.henu.cs.note.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import cn.henu.cs.note.R;
import cn.henu.cs.note.entity.NoteEntity;

public class my_Fragment extends Fragment {
    private List<NoteEntity> favoritesNotes = new ArrayList<NoteEntity>();//笔记数据
    private ListView listView1, listView2, listView3, listView4;

    public my_Fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_my, container, false);
        return v;
    }

}