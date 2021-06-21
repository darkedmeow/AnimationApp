package com.smallgroup.animationapp.domain.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileManager {

    private Context context;
    //init in string.xml
    private final String appDirectoryName = "/Motion Paint";
    private File videoFolder;


    public FileManager(Context context) {
        this.context = context;
        videoFolder = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_MOVIES).getAbsolutePath() + appDirectoryName);
        if (!videoFolder.exists()){
            videoFolder.mkdirs();
        }
    }


    public String createVideo(String title) {
        final String filePath;
        String fileExtn = ".mp4";

        ContentValues valuesvideos = new ContentValues();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            valuesvideos.put(MediaStore.Video.Media.RELATIVE_PATH, videoFolder.getAbsolutePath());
            valuesvideos.put(MediaStore.Video.Media.DATE_TAKEN, System.currentTimeMillis());
        }
        valuesvideos.put(MediaStore.Video.Media.TITLE, title);
        valuesvideos.put(MediaStore.Video.Media.DISPLAY_NAME, title + fileExtn);
        valuesvideos.put(MediaStore.Video.Media.MIME_TYPE, "video/mp4");
        valuesvideos.put(MediaStore.Video.Media.DATE_ADDED, System.currentTimeMillis() / 1000);
        Uri uri = context.getContentResolver().insert(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, valuesvideos);

        // get the path of the video file created in the storage.
        filePath = getRealPathFromUR(uri);
        return filePath;
        }

    public String getRealPathFromUR(Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = { MediaStore.Images.Media.DATA };
            cursor = context.getContentResolver().query(contentUri,  proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } catch (Exception e) {
            Log.e("TAG", "getRealPathFromURI Exception : " + e.toString());
            return "";
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    public void saveBitmap(Bitmap bitmap, String title){
        String path = "" + "/"+ title +".png";
        if(bitmap!=null){
            try {
                FileOutputStream outputStream = null;
                try {
                    outputStream = new FileOutputStream(path);
                    //here is set your file path where you want to save or also here you can set file object directly
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
                    // bitmap is your Bitmap instance, if you want to compress it you can compress reduce percentage
                    // PNG is a lossless format, the compression factor (100) is ignored
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    try {
                        if (outputStream != null) {
                            outputStream.close();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
