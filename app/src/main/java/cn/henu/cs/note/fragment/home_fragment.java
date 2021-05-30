package cn.henu.cs.note.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.widget.SearchView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import cn.henu.cs.note.R;
import cn.henu.cs.note.activity.NoteActivity;
import cn.henu.cs.note.adapter.NoteAdapter;
import cn.henu.cs.note.entity.NoteEntity;
import cn.henu.cs.note.utils.CRUD;
import cn.henu.cs.note.utils.NoteDataBase;
import cn.henu.cs.note.utils.RecyclerItemClickListener;

import static android.content.ContentValues.TAG;

public class home_fragment extends Fragment {

    private FloatingActionButton newNoteBut;
    private RecyclerView recyclerView;
    private NoteDataBase dbHelper;
    private TextView home_toolbar_title;
    private Toolbar homeToolbar;

    private Context context;
    private NoteAdapter adapter;//RecyclerView适配器
    private List<NoteEntity> noteList = new ArrayList<NoteEntity>();//笔记数据


    public home_fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        context = getActivity();
        //Toolbar的操作
        homeToolbar = v.findViewById(R.id.home_toolbar);
        homeToolbar.inflateMenu(R.menu.home_menu);
        //获取到Search搜索框并设置事件
        MenuItem mSearch = homeToolbar.getMenu().findItem(R.id.home_menu_search);
        SearchView mSearchView = (SearchView) mSearch.getActionView();
        mSearchView.setQueryHint("Search");
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });
        //Menu的点击事件
        homeToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home_menu_refresh:
                        Toast.makeText(context, "点击了刷新！", Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.home_menu_deleteAll:
                        Dialog deleteAllDialog = new AlertDialog.Builder(context)
                                .setTitle("删除全部笔记！")
                                .setMessage("确定删除全部笔记?")
                                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dbHelper = new NoteDataBase(context);
                                        SQLiteDatabase db = dbHelper.getWritableDatabase();
                                        db.delete("notes", null, null);
                                        db.execSQL("update sqlite_sequence set seq=0 where name='notes'");
                                        refreshRecyclerView();
                                    }
                                })
                                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Toast.makeText(context, "删除操作取消！", Toast.LENGTH_SHORT).show();
                                    }
                                }).create();
                        deleteAllDialog.show();
                        return true;
                    case R.id.home_menu_settings:
                        Toast.makeText(context, "点击了设置！", Toast.LENGTH_SHORT).show();
                        return true;
                    default:
                        //do nothing
                }
                return false;
            }
        });
        home_toolbar_title = v.findViewById(R.id.home_toolbar_title);
        homeToolbar.setTitle("");
        home_toolbar_title.setText("主页");


        newNoteBut = v.findViewById(R.id.new_note_but);
        recyclerView = v.findViewById(R.id.home_recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new NoteAdapter(context, noteList);

        refreshRecyclerView();
        recyclerView.setAdapter(adapter);
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(context, recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                NoteEntity curNote = noteList.get(position);
                Intent intent = new Intent(context, NoteActivity.class);
                intent.putExtra("content", curNote.getContent());
                intent.putExtra("id", curNote.getId());
                intent.putExtra("time", curNote.getTime());
                intent.putExtra("title", curNote.getTitle());
                intent.putExtra("tag", curNote.getTag());
                intent.putExtra("mode", 3);
                intent.putExtra("favorites", curNote.getFavorites());
                startActivityForResult(intent, 1);
            }

            @Override
            public void onItemLongClick(View view, int position) {
                final String[] items = {"删除该笔记", "添加到我的收藏"};
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        CRUD op = new CRUD(context);
                        switch (which) {
                            case 0://删除第position个笔记
                                op.open();
                                op.removeNote(noteList.get(position));
                                op.close();
                                refreshRecyclerView();
                                Toast.makeText(context, "删除成功", Toast.LENGTH_SHORT).show();
                                break;
                            case 1://将第position个笔记添加到收藏
                                op.open();
                                NoteEntity newNote = noteList.get(position);
                                if (newNote.getFavorites() == 0) {
                                    newNote.setFavorites(1);
                                    op.updateNote(newNote);
                                    op.close();
                                    Toast.makeText(context, "添加成功", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(context, "请勿重复添加", Toast.LENGTH_LONG).show();
                                }
                                break;
                        }
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
            }
        }));
        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    private void refreshRecyclerView() {
        CRUD op = new CRUD(context);
        op.open();
        // set adapter
        if (noteList.size() > 0) noteList.clear();
        noteList.addAll(op.getAllNotes());
        op.close();
        adapter.sortByTime();
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        newNoteBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, NoteActivity.class);
                intent.putExtra("mode", 4);
                startActivityForResult(intent, 0);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        refreshRecyclerView();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        int returnMode;
        long note_Id;
        returnMode = data.getExtras().getInt("mode", -1);
        note_Id = data.getExtras().getLong("id", 0);
        Log.e(TAG, "onActivityResult: 我接受到了删除信息" + returnMode);
        if (returnMode == 1) {  //update current note
            String content = data.getExtras().getString("content");
            if (!content.isEmpty()) {
                String time = data.getExtras().getString("time");
                String title = data.getStringExtra("title");
                int tag = data.getExtras().getInt("tag", 1);
                int favorites = data.getIntExtra("favorites", 0);

                NoteEntity newNote = new NoteEntity(title, content, time, favorites, tag);
                newNote.setId(note_Id);
                CRUD op = new CRUD(context);
                op.open();
                op.updateNote(newNote);
                op.close();
            }
        } else if (returnMode == 0) {
            String content = data.getExtras().getString("content");
            if (!content.isEmpty()) {

                String time = data.getExtras().getString("time");
                String title = data.getStringExtra("title");
                int tag = data.getExtras().getInt("tag", 1);
                int favorites = data.getIntExtra("favorites", 0);

                NoteEntity newNote = new NoteEntity(title, content, time, favorites, tag);
                CRUD op = new CRUD(context);
                op.open();
                op.addNote(newNote);
                op.close();
            }
        } else if (returnMode == 2) { // delete

            NoteEntity curNote = new NoteEntity();
            curNote.setId(note_Id);
            CRUD op = new CRUD(context);
            op.open();
            op.removeNote(curNote);
            op.close();
        } else {
        }
        refreshRecyclerView();
        super.onActivityResult(requestCode, resultCode, data);
    }
}
