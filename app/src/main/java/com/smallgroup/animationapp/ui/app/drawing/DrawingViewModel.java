package com.smallgroup.animationapp.ui.app.drawing;

import android.app.Application;
import android.graphics.Bitmap;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.arthenica.mobileffmpeg.Config;
import com.arthenica.mobileffmpeg.ExecuteCallback;
import com.arthenica.mobileffmpeg.FFmpeg;
import com.smallgroup.animationapp.domain.model.FileManager;

import java.io.File;
import java.util.ArrayList;

import static com.arthenica.mobileffmpeg.Config.RETURN_CODE_CANCEL;
import static com.arthenica.mobileffmpeg.Config.RETURN_CODE_SUCCESS;

public class DrawingViewModel extends AndroidViewModel {

    private final FileManager fileManager;
    MutableLiveData<Boolean> isComplete = new MutableLiveData<>();

    public DrawingViewModel(@NonNull Application application) {
        super(application);
        fileManager = new FileManager(getApplication());
    }


    public void save(ArrayList<Bitmap> bitmapArrayList) {
        //TODO
        //Save in BD

        for (int i = 1; i <= bitmapArrayList.size(); i++) {
            fileManager.saveBitmap(
                    bitmapArrayList.get(i-1),
                    String.format("img%03d", i)
            );
        }
    }

    public void buildVideo(String title, int fps) {

        //path of app folder
        String dirPath = fileManager.getVideoFolderPath();
        //pattern of path img
        String pictures = dirPath + File.separator + "img%03d.png";
        //path of video
        String out = fileManager.createVideo(title);

        //String[] cmd= new String[]{"-framerate", String.valueOf(fps), "-i", pictures, "-vf", "scale=720:480", "-y", out};
        String[] cmd= new String[]{"-framerate", String.valueOf(fps), "-i", pictures, "-vcodec", "mpeg4", "-s", "720x480", "-y", out};

        long executionId = FFmpeg.executeAsync(cmd, new ExecuteCallback() {

            @Override
            public void apply(final long executionId, final int returnCode) {
                if (returnCode == RETURN_CODE_SUCCESS) {
                    isComplete.postValue(true);
                    fileManager.deleteTempImg();
                    Log.i(Config.TAG, "Async command execution completed successfully.");
                } else if (returnCode == RETURN_CODE_CANCEL) {
                    Log.i(Config.TAG, "Async command execution cancelled by user.");
                } else {
                    Log.i(Config.TAG, String.format("Async command execution failed with returnCode=%d.", returnCode));
                }
            }
        });



    }

    public void getListBitmap() {
        //TODO
        //get array from DB
    }

}
