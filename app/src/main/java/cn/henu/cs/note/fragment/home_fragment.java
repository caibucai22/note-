package cn.henu.cs.note.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import cn.henu.cs.note.R;
import cn.henu.cs.note.activity.NoteActivity;
import cn.henu.cs.note.adapter.NoteAdapter;
import cn.henu.cs.note.entity.NoteEntity;
import cn.henu.cs.note.utils.NoteDataBase;

import static android.content.ContentValues.TAG;

public class home_fragment extends Fragment {

    private FloatingActionButton newNoteBut;
    private NoteEntity mNoteEntity;
    private NoteDataBase dbHelper;
    private NoteAdapter adapter;//RecyclerView适配器
    private List<NoteEntity> noteEntities = new ArrayList<>();//笔记数据

    public home_fragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        newNoteBut = v.findViewById(R.id.new_note_but);
        RecyclerView recyclerView=  v.findViewById(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager =new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        List<NoteEntity> datas = new ArrayList<>();
        for (int i=0; i<8;i++) {
            NoteEntity ne = new NoteEntity();

            ne.setTitle("标题标题标题");
            if(i%2==0) {
                ne.setId(R.drawable.item_pic_filetype_txt);
            }else {
                ne.setId(R.drawable.item_pic_filetype_picture);
            }
            ne.setContent("正文正文正文正文正文正文");
            ne.setTime("2021. "+i);
            datas.add(ne);
        }
        NoteAdapter noteAdapter = new NoteAdapter(getActivity(), datas);
        recyclerView.setAdapter(noteAdapter);
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        newNoteBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(getActivity(), NoteActivity.class);
                startActivityForResult(it, 0);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String edit = data.getStringExtra("input");
        //Toast.makeText(getActivity(), edit,Toast.LENGTH_SHORT).show();
    }
}