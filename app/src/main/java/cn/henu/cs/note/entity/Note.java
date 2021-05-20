package cn.henu.cs.note.entity;

import cn.bmob.v3.datatype.BmobFile;

public class Note {
    private String objectId;
    private String noteName;
    private BmobFile note;
    private String noteDate;
    private String url;

    public Note() {
    }

    public String getObjectId() {
        return objectId;
    }

    public void setId(String id) {
        objectId = id;
    }

    public String getNoteName() {
        return noteName;
    }

    public void setNoteName(String noteName) {
        this.noteName = noteName;
    }

    public BmobFile getNote() {
        return note;
    }

    public void setNote(BmobFile note) {
        this.note = note;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
