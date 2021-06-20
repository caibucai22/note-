package cn.henu.cs.note.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import cn.bmob.v3.BmobUser;
import cn.henu.cs.note.R;
import cn.henu.cs.note.Settings;
import cn.henu.cs.note.activity.LoginActivity;
import cn.henu.cs.note.activity.MainActivity;
import cn.henu.cs.note.activity.SettingsActivity;
import cn.henu.cs.note.activity.set;
import cn.henu.cs.note.entity.User;
import cn.henu.cs.note.utils.CRUD;
import cn.henu.cs.note.utils.NoteDataBase;

public class my_Fragment extends Fragment implements View.OnClickListener {

    private RelativeLayout listView1, listView3, listView4;
    private LinearLayout toHomeFragment, toMyLoveFragment;
    private long favoritesNum = 0;
    private long allNotesNum = 0;
    private TextView sumPieces, storePieces, remainNum;
    private TextView userName;
    private EditText mySignature;
    private Context context;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    private Toolbar myToolbar;
    private TextView my_toolbar_title;

    public my_Fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_my, container, false);
        context = getActivity();

        myToolbar = v.findViewById(R.id.my_toolbar);
        my_toolbar_title = v.findViewById(R.id.my_toolbar_title);
        myToolbar.inflateMenu(R.menu.my_menu);
        myToolbar.setTitle("");
        my_toolbar_title.setText("我的");


        myToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.my_menu_setting:
                        Toast.makeText(context, "点击了设置按钮", Toast.LENGTH_SHORT).show();
                        break;
                }
                return false;
            }
        });

        sumPieces = v.findViewById(R.id.my_fragment_pieces);
        storePieces = v.findViewById(R.id.my_fragment_storePieces);
        remainNum = v.findViewById(R.id.my_fragment_remainNum);
        mySignature = v.findViewById(R.id.my_fragment_mySignature);
        userName = v.findViewById(R.id.username_my);

        upData();
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        userName.setText(BmobUser.getCurrentUser(User.class).getUsername());
        if (sharedPreferences.getString("mySignature", "") != null) {
            mySignature.setText(sharedPreferences.getString("mySignature", ""));
        }
        mySignature.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                remainNum.setText("(" + (30 - mySignature.getText().length()) + ")");
            }

            @Override
            public void afterTextChanged(Editable s) {
                editor = sharedPreferences.edit();
                editor.putString("mySignature", mySignature.getText().toString());
                editor.commit();
            }
        });

        toHomeFragment = v.findViewById(R.id.to_home_fragment);
        toHomeFragment.setOnClickListener(this);
        toMyLoveFragment = v.findViewById(R.id.to_my_love_fragment);
        toMyLoveFragment.setOnClickListener(this);
        listView1 = v.findViewById(R.id.listView1);
        listView1.setOnClickListener(this);
        listView3 = v.findViewById(R.id.listView3);
        listView3.setOnClickListener(this);
        listView4 = v.findViewById(R.id.listView4);
        listView4.setOnClickListener(this);
        return v;
    }

    private void upData() {
        CRUD op = new CRUD(context);
        op.open();
        allNotesNum = op.getAllNoteNum();
        favoritesNum = op.getAllFavoritesNoteNum();
        op.close();
        sumPieces.setText("" + allNotesNum);
        storePieces.setText("" + favoritesNum);
        remainNum.setText("(" + (30 - mySignature.getText().length()) + ")");
    }

    @Override
    public void onResume() {
        super.onResume();
        upData();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.listView1:
            case R.id.to_home_fragment:
                Intent intent0 = new Intent(MainActivity.INTENT_FILTER);
                intent0.putExtra(MainActivity.INTENT_NAME,MainActivity.GO_TO_HOME);
                context.sendBroadcast(intent0);
                break;
            case R.id.to_my_love_fragment:
                Intent intent1= new Intent(MainActivity.INTENT_FILTER);
                intent1.putExtra(MainActivity.INTENT_NAME,MainActivity.GO_TO_MY_LOVE);
                context.sendBroadcast(intent1);
                break;
            case R.id.listView3:
                Intent intent = new Intent(context, SettingsActivity.class);
                my_Fragment.this.startActivity(intent);
                break;
            case R.id.listView4:
                Dialog deleteAllDialog = new AlertDialog.Builder(context)
                        .setTitle("退出")
                        .setMessage("确定退出登录?")
                        .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                editor = sharedPreferences.edit();
                                editor.putBoolean("isLogin", false);
                                editor.commit();
                                startActivity(new Intent(context, LoginActivity.class));
                                getActivity().finish();
                            }
                        })
                        .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(context, "取消退出登录！", Toast.LENGTH_SHORT).show();
                            }
                        }).create();
                deleteAllDialog.show();
                break;
        }
    }
}