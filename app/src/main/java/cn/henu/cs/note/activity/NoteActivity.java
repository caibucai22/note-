package cn.henu.cs.note.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.widget.Toolbar;

import java.text.SimpleDateFormat;
import java.util.Date;

import cn.bmob.v3.http.I;
import cn.henu.cs.note.R;

public class NoteActivity extends BaseActivity implements View.OnClickListener {
    private ImageView add_pic, style_bold, style_italics, style_underline,
            style_align_left, style_align_right, style_align_center;
    private EditText note_activity_title, note_activity_content;
    private Toolbar myToolbar;

    //传回的数据
    private String old_content = "";
    private String old_time = "";
    private String old_title = "";
    private int old_Tag = 1;
    private long id = 0;
    private int openMode = 0;    //4：代表是新建的笔记， 3：代表是打开原来的笔记
    private int tag = 1;
    private Intent intent = new Intent();
    private boolean tagChange = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int initLayout() {
        return R.layout.activity_note;
    }

    @Override
    protected void initView() {
        myToolbar = findViewById(R.id.note_toolbar);


        add_pic = findViewById(R.id.add_pic);
        style_bold = findViewById(R.id.style_bold);
        style_italics = findViewById(R.id.style_italics);
        style_underline = findViewById(R.id.style_underline);
        style_align_left = findViewById(R.id.style_align_left);
        style_align_right = findViewById(R.id.style_align_right);
        style_align_center = findViewById(R.id.style_align_center);

        note_activity_title = findViewById(R.id.note_activity_title);
        note_activity_content = findViewById(R.id.note_activity_content);

    }

    @Override
    protected void initData() {
        setSupportActionBar(myToolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //设置toolbar取代actionBar

        myToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                autoSetMessage();
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        add_pic.setOnClickListener(this);
        style_bold.setOnClickListener(this);
        style_italics.setOnClickListener(this);
        style_underline.setOnClickListener(this);
        style_align_left.setOnClickListener(this);
        style_align_right.setOnClickListener(this);
        style_align_center.setOnClickListener(this);

        Intent getIntent = getIntent();
        openMode = getIntent.getIntExtra("mode", 0);

        if (openMode == 3) {
            id = getIntent.getLongExtra("id", 0);
            old_content = getIntent.getStringExtra("content");
            old_Tag = getIntent.getIntExtra("tag", 1);
            old_time = getIntent.getStringExtra("time");
            old_title = getIntent.getStringExtra("title");

            note_activity_title.setText(old_title);
            if (old_title.length() != 0) {
                note_activity_title.setSelection(old_title.length());
            }
            note_activity_content.setText(old_content);
            note_activity_content.setSelection(old_content.length());
        }
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_HOME) {
            return true;
        } else if (keyCode == KeyEvent.KEYCODE_BACK) {
            autoSetMessage();
            setResult(RESULT_OK, intent);
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void autoSetMessage() {
        if (openMode == 4) {
            if (note_activity_content.getText().toString().length() == 0) {
                intent.putExtra("mode", -1); //nothing new happens.
            } else {
                intent.putExtra("mode", 0); // new one note;
                intent.putExtra("content", note_activity_content.getText().toString());
                intent.putExtra("time", dateToStr());
                intent.putExtra("title", note_activity_title.getText().toString());
                intent.putExtra("tag", tag);
            }
        } else {
            if (note_activity_content.getText().toString().equals(old_content) && note_activity_title.getText().toString().equals(old_title) && !tagChange)
                intent.putExtra("mode", -1); // edit nothing
            else {
                intent.putExtra("mode", 1); //edit the content
                intent.putExtra("content", note_activity_content.getText().toString());
                intent.putExtra("time", dateToStr());
                intent.putExtra("id", id);
                intent.putExtra("title", note_activity_title.getText().toString());
                intent.putExtra("tag", tag);
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

        }
    }

    //获取当前系统时间
    private String dateToStr() {
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return simpleDateFormat.format(date);
    }
}