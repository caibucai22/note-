package cn.henu.cs.note.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

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

public class favorites_Fragment extends Fragment {

    private RecyclerView favoritesRecyclerView;
    private NoteDataBase dbHelper;

    private Toolbar favoritesToolbar;

    private Context context;
    private NoteAdapter favoritesAdapter;//RecyclerView适配器
    private List<NoteEntity> favoritesNotes = new ArrayList<NoteEntity>();//笔记数据

    public favorites_Fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_favorites, container, false);
        context = getActivity();
        //toolbar设置
        favoritesToolbar = v.findViewById(R.id.favorites_toolbar);
        favoritesToolbar.inflateMenu(R.menu.favorites_menu);
        favoritesToolbar.setTitle("收藏");

        //获取到Search搜索框并设置事件
        MenuItem mSearch = favoritesToolbar.getMenu().findItem(R.id.favorites_menu_search);
        SearchView mSearchView = (SearchView) mSearch.getActionView();
        mSearchView.setQueryHint("Search your favorites");
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                favoritesAdapter.getFilter().filter(newText);
                return false;
            }
        });
        MenuItem deleteAll = favoritesToolbar.getMenu().findItem(R.id.favorites_menu_deleteAll);
        deleteAll.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Dialog deleteAllDialog = new AlertDialog.Builder(context)
                        .setMessage("确定取消收藏全部笔记?")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                CRUD op = new CRUD(context);
                                op.open();
                                for (int i=0; i<favoritesNotes.size(); i++) {
                                    favoritesNotes.get(i).setFavorites(0);
                                    op.updateNote(favoritesNotes.get(i));
                                }
                                op.close();
                                refreshRecyclerView();
                                Toast.makeText(context, "全部取消收藏", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(context, "取消收藏操作取消！", Toast.LENGTH_SHORT).show();
                            }
                        }).create();
                deleteAllDialog.show();
                return false;
            }
        });

        //recyclerView相关设置
        favoritesRecyclerView = v.findViewById(R.id.favorites_recycleView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        favoritesRecyclerView.setLayoutManager(linearLayoutManager);
        favoritesAdapter = new NoteAdapter(context, favoritesNotes);

        refreshRecyclerView();
        favoritesRecyclerView.setAdapter(favoritesAdapter);
        favoritesRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(context, favoritesRecyclerView, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                NoteEntity curNote = favoritesNotes.get(position);
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
                final String[] items = {"删除该笔记", "取消收藏"};
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        CRUD op = new CRUD(context);
                        switch (which) {
                            case 0://删除第position个笔记
                                op.open();
                                op.removeNote(favoritesNotes.get(position));
                                op.close();
                                Toast.makeText(context, "删除成功", Toast.LENGTH_SHORT).show();
                                break;
                            case 1://将第position个笔记取消收藏
                                op.open();
                                NoteEntity newNote = favoritesNotes.get(position);
                                newNote.setFavorites(0);
                                op.updateNote(newNote);
                                op.close();
                                Toast.makeText(context, "取消成功", Toast.LENGTH_SHORT).show();
                                break;
                        }
                        refreshRecyclerView();
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
            }
        }));
        return v;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        int returnMode;
        long note_Id;
        returnMode = data.getExtras().getInt("mode", -1);
        note_Id = data.getExtras().getLong("id", 0);

        if (returnMode == 1) {  //update current note
            String content = data.getExtras().getString("content");
            if (!content.isEmpty()) {
                String time = data.getExtras().getString("time");
                String title = data.getStringExtra("title");
                int tag = data.getExtras().getInt("tag", 1);
                int favorites = data.getIntExtra("favorites", 0);
                NoteEntity newNote = new NoteEntity(title, content, time, favorites,tag);

                newNote.setId(note_Id);
                CRUD op = new CRUD(context);
                op.open();
                op.updateNote(newNote);
                op.close();
            }
        }
//        else if (returnMode == 0) {
//            String content = data.getExtras().getString("content");
//            if (!content.isEmpty()) {
//
//                String time = data.getExtras().getString("time");
//                String title = data.getStringExtra("title");
//                int tag = data.getExtras().getInt("tag", 1);
//
//                NoteEntity newNote = new NoteEntity(title, content, time, tag);
//                CRUD op = new CRUD(context);
//                op.open();
//                op.addNote(newNote);
//                op.close();
//            }
//
//        } else {
//        }
        refreshRecyclerView();
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void getFavoritesNotes(int favorites) {
        CRUD op = new CRUD(context);
        op.open();
        favoritesNotes = op.getFavoritesNotes(favorites);
        op.close();
    }

    private void refreshRecyclerView() {
        CRUD op = new CRUD(context);
        op.open();
        // set adapter
        if (favoritesNotes.size() > 0) favoritesNotes.clear();
        favoritesNotes.addAll(op.getFavoritesNotes(1));
        op.close();
        favoritesAdapter.notifyDataSetChanged();
    }

    @Override
    public void onResume() {
        super.onResume();
        refreshRecyclerView();
    }

}