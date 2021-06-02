package cn.henu.cs.note.entity;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class NoteEntity implements Serializable {
    private long Id;
    private String title;
    private String content;
    private String time;
    private int tag;
    private int favorites;
    private User author;

    public NoteEntity() {

    }


    public NoteEntity(String content, String time, int tag) {
        this.content = content;
        this.time = time;
        this.tag = tag;
    }

    public NoteEntity(String title, String content, String time, int tag) {
        this.title = title;
        this.content = content;
        this.time = time;
        this.tag = tag;
    }

    public NoteEntity(String title, String content, String time, int favorites, int tag) {
        this.title = title;
        this.content = content;
        this.time = time;
        this.favorites = favorites;
        this.tag = tag;
    }

    public int getFavorites() {
        return favorites;
    }

    public void setFavorites(int favorites) {
        this.favorites = favorites;
    }

    public long getId() {
        return Id;
    }

    public void setId(long id) {
        Id = id;
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

    public int getTag() {
        return tag;
    }

    public void setTag(int tag) {
        this.tag = tag;
    }

    @NonNull
    @Override
    public String toString() {
        return content + "\n" + time.substring(5, 6) + " " + Id;
    }

    //使显示到RecyclerView的信息格式化
    public String formatTitle() {
        String result;
        if (title.isEmpty()) {
            result = content.substring(0, 20) + "...";
        }
        if (title.length() > 20) {
            result = title.substring(0, 20) + "...";
        } else {
            result = title;
        }
        return result;
    }

}
