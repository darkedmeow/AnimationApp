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

import com.arthenica.mobileffmpeg.Config;
import com.arthenica.mobileffmpeg.ExecuteCallback;
import com.arthenica.mobileffmpeg.FFmpeg;

import java.io.File;
import java.io.FileFilter;
import java.io.FileOutputStream;
import java.io.IOException;

import static com.arthenica.mobileffmpeg.Config.RETURN_CODE_CANCEL;
import static com.arthenica.mobileffmpeg.Config.RETURN_CODE_SUCCESS;

public class FileManager {

    private Context context;
    //init in string.xml
    private final String appDirectoryName = "/Motion Paint";
    private File videoFolder;


    public FileManager(Context context) {
        this.context = context;
        videoFolder = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + appDirectoryName);
        if (!videoFolder.exists()){
            videoFolder.mkdirs();
        }
    }

    public String getVideoFolderPath() {
        return videoFolder.getAbsolutePath();
    }

    public String createVideo(String title) {
        String fileExtn = ".mp4";

        File videoFile = new File(videoFolder + "/" + title + fileExtn);

        ContentValues valuesvideos = new ContentValues();
        valuesvideos.put(MediaStore.Video.Media.DATA, videoFile.getAbsolutePath());
        valuesvideos.put(MediaStore.Video.Media.TITLE, title);
        valuesvideos.put(MediaStore.Video.Media.DISPLAY_NAME, title + fileExtn);
        valuesvideos.put(MediaStore.Video.Media.MIME_TYPE, "video/mp4");
        valuesvideos.put(MediaStore.Video.Media.DATE_ADDED, System.currentTimeMillis() / 1000);
        Uri uri = context.getContentResolver().insert(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, valuesvideos);

        // get the path of the video file created in the storage.
        return videoFile.getAbsolutePath();
        }

    //Delete
    public String getRealPathFromUR(Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = { MediaStore.Images.Media.DATA };
            cursor = context.getContentResolver().query(contentUri,  proj, null, null, null);
            int column_index = 0;
            if (cursor != null) {
                column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            }
            else{
                Log.e("CURSOR", "error");
            }
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

    public void deleteTempImg() {
        String[] files = videoFolder.list();
        String reg = "^.*\\.png$";
        for (String file : files) {
            if (file.matches(reg)) {
                new File(videoFolder, file).delete();
            }
        }
    }

    public void saveBitmap(Bitmap bitmap, String title){
        String path = videoFolder + "/"+ title +".png";
        if(bitmap!=null){
            try {
                FileOutputStream outputStream = null;
                try {
                    outputStream = new FileOutputStream(path);
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
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
