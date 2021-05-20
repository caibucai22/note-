package cn.henu.cs.note.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.view.textclassifier.ConversationAction;

import java.util.ArrayList;
import java.util.List;

import cn.henu.cs.note.entity.NoteEntity;

public class CRUD {
    SQLiteOpenHelper dbHandler;
    SQLiteDatabase db;

    private static final String[] columns = {
            NoteDataBase.ID,
            NoteDataBase.CONTENT,
            NoteDataBase.TIME,
            NoteDataBase.MODE
    };

    public CRUD(Context context) {
        dbHandler = new NoteDataBase(context);
    }

    public void open(){
        db = dbHandler.getWritableDatabase();
    }

    public void close(){
        dbHandler.close();
    }

    //把note 加入到database里面
    public NoteEntity addNote(NoteEntity note){
        //add a note object to database
        ContentValues contentValues = new ContentValues();
        contentValues.put(NoteDataBase.CONTENT, note.getContent());
        contentValues.put(NoteDataBase.TIME, note.getTime());
        contentValues.put(NoteDataBase.MODE, note.getTag());
        long insertId = db.insert(NoteDataBase.TABLE_NAME, null, contentValues);
        note.setId(insertId);
        return note;
    }

    public NoteEntity getNote(long id){
        //get a note from database using cursor index
        Cursor cursor = db.query(NoteDataBase.TABLE_NAME, columns, NoteDataBase.ID + "=?",
                new String[] {String.valueOf(id)}, null, null, null, null);
        if (cursor != null) cursor.moveToFirst();
        NoteEntity e = new NoteEntity(cursor.getString(1), cursor.getString(2), cursor.getInt(3));
        return e;
    }

    public List<NoteEntity> getAllNotes(){
        Cursor cursor = db.query(NoteDataBase.TABLE_NAME, columns, null, null, null, null, null);

        List<NoteEntity> notes = new ArrayList<>();
        if (cursor.getCount() > 0){
            while (cursor.moveToNext()){
                NoteEntity note = new NoteEntity();
                note.setId(cursor.getLong(cursor.getColumnIndex(NoteDataBase.ID)));
                note.setContent(cursor.getString(cursor.getColumnIndex(NoteDataBase.CONTENT)));
                note.setTime(cursor.getString(cursor.getColumnIndex(NoteDataBase.TIME)));
                note.setTag(cursor.getInt(cursor.getColumnIndex(NoteDataBase.MODE)));
                notes.add(note);
            }
        }
        return notes;
    }

    public int updateNote(NoteEntity note) {
        //update the info of an existing note
        ContentValues values = new ContentValues();
        values.put(NoteDataBase.CONTENT, note.getContent());
        values.put(NoteDataBase.TIME, note.getTime());
        values.put(NoteDataBase.MODE, note.getTag());
        //updating row
        return db.update(NoteDataBase.TABLE_NAME, values,
                NoteDataBase.ID + "=?", new String[] { String.valueOf(note.getId())});
    }

    public void removeNote(NoteEntity note){
        //remove a note according to ID value
        db.delete(NoteDataBase.TABLE_NAME, NoteDataBase.ID + "=" + note.getId(), null);
    }

}
