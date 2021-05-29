package cn.henu.cs.note.fragment;

import android.content.Context;
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
import cn.henu.cs.note.adapter.NoteAdapter;
import cn.henu.cs.note.entity.NoteEntity;
import cn.henu.cs.note.utils.CRUD;
import cn.henu.cs.note.utils.NoteDataBase;

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
                Toast.makeText(context, "删除所有", Toast.LENGTH_SHORT).show();
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

        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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