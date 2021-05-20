package cn.henu.cs.note.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import cn.bmob.v3.http.I;
import cn.henu.cs.note.R;
import cn.henu.cs.note.entity.NoteEntity;

public class NoteActivity extends BaseActivity implements View.OnClickListener {
    private ImageView note_activity_back, note_activity_complete;
    private ImageView add_pic, style_bold, style_italics, style_underline,
            style_align_left, style_align_right, style_align_center;
    private EditText title, content;
    private NoteEntity noteEntity;

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
        note_activity_back = findViewById(R.id.note_activity_back);
        note_activity_complete = findViewById(R.id.note_activity_complete);

        add_pic = findViewById(R.id.add_pic);
        style_bold = findViewById(R.id.style_bold);
        style_italics = findViewById(R.id.style_italics);
        style_underline = findViewById(R.id.style_underline);
        style_align_left = findViewById(R.id.style_align_left);
        style_align_right = findViewById(R.id.style_align_right);
        style_align_center = findViewById(R.id.style_align_center);

        title = findViewById(R.id.note_activity_title);
        content = findViewById(R.id.note_activity_content);

    }

    @Override
    protected void initData() {
        note_activity_back.setOnClickListener(this);
        note_activity_complete.setOnClickListener(this);
        add_pic.setOnClickListener(this);
        style_bold.setOnClickListener(this);
        style_italics.setOnClickListener(this);
        style_underline.setOnClickListener(this);
        style_align_left.setOnClickListener(this);
        style_align_right.setOnClickListener(this);
        style_align_center.setOnClickListener(this);

        //noteEntity.setTitle(title.getText().toString());
        //noteEntity.setContent(content.getText().toString());

    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_HOME) {
            return true;
        } else if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent = new Intent();
            intent.putExtra("input", content.getText().toString());
            setResult(RESULT_OK, intent);
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.note_activity_back:
                Intent intent = new Intent();
                intent.putExtra("input", content.getText().toString());
                setResult(RESULT_OK, intent);
                finish();
                finish();
                break;
            case R.id.note_activity_complete:
                break;
        }
    }
}