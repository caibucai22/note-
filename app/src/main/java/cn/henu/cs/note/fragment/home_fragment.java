package cn.henu.cs.note.fragment;

import android.content.Context;
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
import android.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import cn.henu.cs.note.R;
import cn.henu.cs.note.activity.NoteActivity;
import cn.henu.cs.note.adapter.NoteAdapter;
import cn.henu.cs.note.entity.NoteEntity;
import cn.henu.cs.note.utils.CRUD;
import cn.henu.cs.note.utils.NoteDataBase;

import static android.content.ContentValues.TAG;

public class home_fragment extends Fragment {

    private FloatingActionButton newNoteBut;
    private RecyclerView recyclerView;
    private NoteDataBase dbHelper;

    private Context context;
    private NoteAdapter adapter;//RecyclerView适配器
    private List<NoteEntity> noteList = new ArrayList<NoteEntity>();//笔记数据

    public home_fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        context = getActivity();
        newNoteBut = v.findViewById(R.id.new_note_but);
        recyclerView = v.findViewById(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new NoteAdapter(context, noteList);
        adapter.setOnItemClickListener(new NoteAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(context,"这是第"+noteList.get(position).getId()+"笔记",Toast.LENGTH_SHORT).show();
            }
        });
        refreshRecyclerView();
        recyclerView.setAdapter(adapter);
        return v;
    }

    private void refreshRecyclerView() {
        CRUD op = new CRUD(context);
        op.open();
        // set adapter
        if (noteList.size() > 0) noteList.clear();
        noteList.addAll(op.getAllNotes());
        op.close();
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        newNoteBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(context, NoteActivity.class);
                startActivityForResult(it, 0);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String content = data.getStringExtra("content");
        if (content.isEmpty()) {

        } else {
            String time = data.getStringExtra("time");
            String title = data.getStringExtra("title");
            int tag = data.getIntExtra("tag", 1);
            NoteEntity note = new NoteEntity(title,content, time, 1);
            CRUD op = new CRUD(context);
            op.open();
            op.addNote(note);
            op.close();
            refreshRecyclerView();
        }
    }
}