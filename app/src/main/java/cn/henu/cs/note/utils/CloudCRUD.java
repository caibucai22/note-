package cn.henu.cs.note.utils;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobBatch;
import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BatchResult;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListListener;
import cn.bmob.v3.listener.SaveListener;
import cn.henu.cs.note.entity.NoteEntity;

public class CloudCRUD {

    private CRUD crud;
    private Context context;

    public CloudCRUD(Context context) {
        this.context = context;
    }

    public void uploadNote(long id) {
        crud = new CRUD(context);
        crud.open();
        NoteEntity entity = crud.getNote(id);
        entity.save(new SaveListener<String>() {
            @Override
            public void done(String objectId, BmobException e) {
                if (e == null) {
                    Toast.makeText(context, "笔记保存到云端", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "笔记保存到云端失败", Toast.LENGTH_SHORT).show();
                    Log.e("BMOB", e.toString());
                }
            }
        });
    }

    public void uploadNotes() {
        crud = new CRUD(context);
        crud.open();
        List<BmobObject> entities =new ArrayList<>();
        for (NoteEntity entity : crud.getAllNotes()) {
//            if(query(entity.getId()))
            entities.add(entity);
        }
        crud.close();

        new BmobBatch().insertBatch(entities).doBatch(new QueryListListener<BatchResult>() {
            @Override
            public void done(List<BatchResult> results, BmobException e) {
                if (e == null) {
                    for (int i = 0; i < results.size(); i++) {
                        BatchResult result = results.get(i);
                        BmobException ex = result.getError();
                        if (ex == null) {
                            Toast.makeText(context, "第"+(i+1)+"笔记保存到云端", Toast.LENGTH_SHORT).show();
                        } else {

                        }
                    }
                } else {
                    Toast.makeText(context, "笔记保存到云端失败", Toast.LENGTH_SHORT).show();
                    Log.e("BMOB", e.toString());
                }
            }
        });
    }

//    private boolean query(long id) {
//
//    }


}



