package cn.henu.cs.note.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import cn.henu.cs.note.R;
import cn.henu.cs.note.adapter.NoteAdapter;
import cn.henu.cs.note.entity.NoteEntity;

public class home_fragment extends Fragment {

    public home_fragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_home_fragment, container, false);
        RecyclerView recyclerView=  v.findViewById(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager =new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        List<NoteEntity> datas = new ArrayList<>();
        for (int i=0; i<8;i++) {
            NoteEntity ne = new NoteEntity();

            ne.setTitle("标题标题标题");
            if(i%2==0) {
                ne.setPicId(R.drawable.item_pic_filetype_txt);
            }else {
                ne.setPicId(R.drawable.item_pic_filetype_picture);
            }
            ne.setContent("正文正文正文正文正文正文");
            ne.setTime("2021. "+i);
            datas.add(ne);
        }
        NoteAdapter noteAdapter = new NoteAdapter(getActivity(), datas);
        recyclerView.setAdapter(noteAdapter);
        return v;
    }
}