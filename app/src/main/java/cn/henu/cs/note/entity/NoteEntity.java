package cn.henu.cs.note.entity;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class NoteEntity implements Serializable {
    private long Id;
    private String title;
    private String content;
    private String time;
    private int tag;

    public NoteEntity() {

    }

    public NoteEntity(String content, String time, int tag) {
        this.content = content;
        this.time = time;
        this.tag = tag;
    }

    public int getTag() {
        return tag;
    }

    public void setTag(int tag) {
        this.tag = tag;
    }

    public void setId(long id) {
        Id = id;
    }

    public long getId() {
        return Id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @NonNull
    @Override
    public String toString() {
        return content + "\n" + time.substring(5, 6) + " " + Id;
    }
}
