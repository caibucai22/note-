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
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.henu.cs.note.R;
import cn.henu.cs.note.activity.NoteActivity;
import cn.henu.cs.note.adapter.NoteAdapter;
import cn.henu.cs.note.entity.NoteEntity;
import cn.henu.cs.note.entity.User;
import cn.henu.cs.note.utils.CRUD;
import cn.henu.cs.note.utils.NoteDataBase;
import cn.henu.cs.note.utils.RecyclerItemClickListener;

import static android.content.ContentValues.TAG;

public class favorites_Fragment extends Fragment {

    private RecyclerView favoritesRecyclerView;
    private NoteDataBase dbHelper;

    private Toolbar favoritesToolbar;
    private TextView favorites_toolbar_title;

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
        favorites_toolbar_title = v.findViewById(R.id.favorites_toolbar_title);
        favoritesToolbar.setTitle("");
        favorites_toolbar_title.setText("收藏");

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
                if (NoteEntity.FavoriteNotesNumber() > 0) {
                    Dialog deleteAllDialog = new AlertDialog.Builder(context)
                            .setMessage("确定取消收藏全部笔记?")
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    CRUD op = new CRUD(context);
                                    op.open();
                                    for (int i = 0; i < favoritesNotes.size(); i++) {
                                        favoritesNotes.get(i).setFavorites(0);
                                        op.updateNote(favoritesNotes.get(i));
                                        favoritesNotes.get(i).update(new UpdateListener() {
                                            @Override
                                            public void done(BmobException e) {
                                                if (e == null) {
                                                    Log.e("TAG", "done: 数据更新成功");
                                                } else {
                                                    Log.e("BMOB", e.toString());
                                                    Log.e("TAG", "done: 数据更新失败");
                                                }
                                            }
                                        });
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
                } else {
                    Toast.makeText(context, "没有收藏任何笔记", Toast.LENGTH_SHORT).show();
                }
                return false;
            }
        });

        //recyclerView相关设置
        favoritesRecyclerView = v.findViewById(R.id.favorites_recycleView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        favoritesRecyclerView.setLayoutManager(linearLayoutManager);
        favoritesAdapter = new NoteAdapter(context, favoritesNotes);
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
                intent.putExtra("object_id", curNote.getObjectId());
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
                                favoritesNotes.get(position).delete(new UpdateListener() {
                                    @Override
                                    public void done(BmobException e) {
                                        if (e == null) {
                                            Log.e("TAG", "done: 数据删除成功");
                                        } else {
                                            Log.e("BMOB", e.toString());
                                            Log.e("TAG", "done: 数据删除失败");
                                        }
                                    }
                                });
                                op.open();
                                op.removeNote(favoritesNotes.get(position));
                                op.close();
                                Toast.makeText(context, "删除成功", Toast.LENGTH_SHORT).show();
                                break;
                            case 1://将第position个笔记取消收藏
                                op.open();
                                NoteEntity newNote = favoritesNotes.get(position);
                                newNote.setFavorites(0);
                                newNote.update(new UpdateListener() {
                                    @Override
                                    public void done(BmobException e) {
                                        if (e == null) {
                                            Log.e("TAG", "done: 数据更新成功");
                                        } else {
                                            Log.e("BMOB", e.toString());
                                            Log.e("TAG", "done: 数据更新失败");
                                        }
                                    }
                                });
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
                String object_id = data.getStringExtra("object_id");

                NoteEntity newNote = new NoteEntity(title, content, time, favorites, tag, object_id);
                newNote.setId(note_Id);
                newNote.update(new UpdateListener() {
                    @Override
                    public void done(BmobException e) {
                        if (e == null) {
                            Log.e("TAG", "done: 数据更新成功");
                        } else {
                            Log.e("BMOB", e.toString());
                            Log.e("TAG", "done: 数据更新失败");
                        }
                    }
                });
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

                newNote.setAuthor(BmobUser.getCurrentUser(User.class));
                newNote.save(new SaveListener<String>() {
                    @Override
                    public void done(String s, BmobException e) {
                        if (e == null) {
                            Log.e("TAG", "done: 数据保存云端成功");
                            newNote.setObjectId(s);
                            CRUD op = new CRUD(context);
                            op.open();
                            op.addNote(newNote);
                            Log.e("TAG", "done: 数据保存本地成功");
                            op.close();
                            Log.e(TAG, "onActivityResult: refreshRecyclerView");
                            refreshRecyclerView();
                        } else {
                            Log.e("BMOB", e.toString());
                        }
                    }
                });
            }
        } else if (returnMode == 2) { // delete
            NoteEntity curNote = new NoteEntity();
            String object_id = data.getExtras().getString("object_id");
            curNote.setId(note_Id);
            curNote.setObjectId(object_id);
            curNote.delete(object_id, new UpdateListener() {
                @Override
                public void done(BmobException e) {
                    if (e == null) {
                        Log.e("TAG", "done: 数据删除成功");
                    } else {
                        Log.e("BMOB", e.toString());
                        Log.e("TAG", "done: 数据删除失败");
                    }
                }
            });
            CRUD op = new CRUD(context);
            op.open();
            op.removeNote(curNote);
            op.close();
            Log.e(TAG, "onActivityResult: refreshRecyclerView");
            refreshRecyclerView();
        } else {
        }
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
        favoritesAdapter.sortByTime();
        favoritesAdapter.notifyDataSetChanged();
    }

    @Override
    public void onResume() {
        super.onResume();
        refreshRecyclerView();
    }

}