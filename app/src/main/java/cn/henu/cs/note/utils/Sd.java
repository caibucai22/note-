package cn.henu.cs.note.utils;

import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;

public class Sd {
    public static boolean confirm(){
        boolean mExternalStorageAvailable=false;
        boolean mExternalStorageWriteable=false;
        String state = Environment.getExternalStorageState();
        if(Environment.MEDIA_MOUNTED.equals(state)){
            //外部存储器可读写
            mExternalStorageAvailable = mExternalStorageWriteable = true;
        }else if(Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)){
            //外部存储器可读不可写
            mExternalStorageAvailable=true;
            mExternalStorageWriteable=false;
        }else{
            //外部存储器不可读写，处于其他状态
            mExternalStorageAvailable = mExternalStorageWriteable = false;
        }

        return mExternalStorageAvailable&mExternalStorageWriteable;
    }

    public static void writeFile(String filename,String data){

        if(confirm()){//获取外部设备状态 判断外部设备是否可用
            String path = Environment.getExternalStorageDirectory().getAbsolutePath()+filename;
            File file = new File(path);
            try{
                if(!file.exists()){
                    file.createNewFile();
                }
                FileOutputStream fos = new FileOutputStream(file);
                fos.write(data.getBytes());
                fos.close();
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }

    public static void readFile(String filename){
        if(confirm()){

        }
    }
}
