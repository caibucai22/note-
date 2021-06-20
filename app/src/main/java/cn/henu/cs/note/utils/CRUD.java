package cn.henu.cs.note.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.view.textclassifier.ConversationAction;
import android.widget.ListAdapter;

import java.util.ArrayList;
import java.util.List;

import cn.henu.cs.note.entity.NoteEntity;

import static android.service.controls.ControlsProviderService.TAG;

public class CRUD {
    SQLiteOpenHelper dbHandler;
    SQLiteDatabase db;

    private static final String[] columns = {
            NoteDataBase.ID,
            NoteDataBase.TITLE,
            NoteDataBase.CONTENT,
            NoteDataBase.TIME,
            NoteDataBase.FAVORITES,
            NoteDataBase.MODE,
            NoteDataBase.OBJECT_ID
    };

    public CRUD(Context context) {
        dbHandler = new NoteDataBase(context);
    }

    public void open() {
        db = dbHandler.getWritableDatabase();
    }

    public void close() {
        dbHandler.close();
    }

    //把note 加入到database里面
    public NoteEntity addNote(NoteEntity note) {
        //add a note object to database
        ContentValues contentValues = new ContentValues();
        contentValues.put(NoteDataBase.TITLE, note.getTitle());
        contentValues.put(NoteDataBase.CONTENT, note.getContent());
        contentValues.put(NoteDataBase.TIME, note.getTime());
        contentValues.put(NoteDataBase.FAVORITES, note.getFavorites());
        contentValues.put(NoteDataBase.MODE, note.getTag());
        contentValues.put(NoteDataBase.OBJECT_ID, note.getObjectId());
        long insertId = db.insert(NoteDataBase.TABLE_NAME, null, contentValues);
        note.setId(insertId);
        return note;
    }
    public void addAllNotes(List<NoteEntity> list){
        ContentValues contentValues = new ContentValues();
        for(int i=0; i<list.size(); i++){
            contentValues.put(NoteDataBase.TITLE, list.get(i).getTitle());
            contentValues.put(NoteDataBase.CONTENT, list.get(i).getContent());
            contentValues.put(NoteDataBase.TIME, list.get(i).getTime());
            contentValues.put(NoteDataBase.FAVORITES, list.get(i).getFavorites());
            contentValues.put(NoteDataBase.MODE, list.get(i).getTag());
            contentValues.put(NoteDataBase.OBJECT_ID, list.get(i).getObjectId());
            long insertId = db.insert(NoteDataBase.TABLE_NAME, null, contentValues);
            list.get(i).setId(insertId);
        }
    }
    //查询一个note
    public NoteEntity getNote(long id) {
        //get a note from database using cursor index
        Cursor cursor = db.query(NoteDataBase.TABLE_NAME, columns, NoteDataBase.ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null) cursor.moveToFirst();
        NoteEntity e = new NoteEntity(cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getInt(4), cursor.getInt(5), cursor.getString(6));
        return e;
    }


    public List<NoteEntity> getAllNotes() {
        Cursor cursor = db.query(NoteDataBase.TABLE_NAME, columns, null, null, null, null, null);

        List<NoteEntity> notes = new ArrayList<>();
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                NoteEntity note = new NoteEntity();
                note.setId(cursor.getLong(cursor.getColumnIndex(NoteDataBase.ID)));
                note.setTitle(cursor.getString(cursor.getColumnIndex(NoteDataBase.TITLE)));
                note.setContent(cursor.getString(cursor.getColumnIndex(NoteDataBase.CONTENT)));
                note.setTime(cursor.getString(cursor.getColumnIndex(NoteDataBase.TIME)));
                note.setFavorites(cursor.getInt(cursor.getColumnIndex(NoteDataBase.FAVORITES)));
                note.setTag(cursor.getInt(cursor.getColumnIndex(NoteDataBase.MODE)));
                note.setObjectId(cursor.getString(cursor.getColumnIndex(NoteDataBase.OBJECT_ID)));
                notes.add(note);
            }
        }
        return notes;
    }
    public List<NoteEntity> getFavoritesNotes(int favorites) {

        Cursor cursor = db.query(NoteDataBase.TABLE_NAME, columns, null, null, null, null, null);

        List<NoteEntity> notes = new ArrayList<>();
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                if(cursor.getInt(cursor.getColumnIndex(NoteDataBase.FAVORITES))==favorites) {
                    NoteEntity note = new NoteEntity();
                    note.setId(cursor.getLong(cursor.getColumnIndex(NoteDataBase.ID)));
                    note.setTitle(cursor.getString(cursor.getColumnIndex(NoteDataBase.TITLE)));
                    note.setContent(cursor.getString(cursor.getColumnIndex(NoteDataBase.CONTENT)));
                    note.setTime(cursor.getString(cursor.getColumnIndex(NoteDataBase.TIME)));
                    note.setFavorites(cursor.getInt(cursor.getColumnIndex(NoteDataBase.FAVORITES)));
                    note.setTag(cursor.getInt(cursor.getColumnIndex(NoteDataBase.MODE)));
                    note.setObjectId(cursor.getString(cursor.getColumnIndex(NoteDataBase.OBJECT_ID)));
                    notes.add(note);
                }
            }
        }
        return notes;
    }
    public int updateNote(NoteEntity note) {
        //update the info of an existing note
        ContentValues values = new ContentValues();
        values.put(NoteDataBase.TITLE, note.getTitle());
        values.put(NoteDataBase.CONTENT, note.getContent());
        values.put(NoteDataBase.TIME, note.getTime());
        values.put(NoteDataBase.FAVORITES, note.getFavorites());
        values.put(NoteDataBase.MODE, note.getTag());
        values.put(NoteDataBase.OBJECT_ID, note.getObjectId());
        //updating row
        return db.update(NoteDataBase.TABLE_NAME, values,
                NoteDataBase.ID + "=?", new String[]{String.valueOf(note.getId())});
    }

    public void removeNote(NoteEntity note) {
        //remove a note according to ID value
        db.delete(NoteDataBase.TABLE_NAME, NoteDataBase.ID + "=" + note.getId(), null);
    }

    public void removeAllNote(List<NoteEntity> notes) {
        for (int i = 0; i < notes.size(); i++) {
            db.delete(NoteDataBase.TABLE_NAME, NoteDataBase.ID + "=" + notes.get(i).getId(), null);
        }
    }

    public long getAllNoteNum() {
        Cursor cursor = db.query(NoteDataBase.TABLE_NAME, columns, null, null, null, null, null);
        return cursor.getCount();
    }

    public long getAllFavoritesNoteNum() {
        long num=0;
        Cursor cursor = db.query(NoteDataBase.TABLE_NAME, columns, null, null, null, null, null);
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                if(cursor.getInt(cursor.getColumnIndex(NoteDataBase.FAVORITES))!=0) {
                    num++;
                }
            }
        }
        return num;
    }
}
