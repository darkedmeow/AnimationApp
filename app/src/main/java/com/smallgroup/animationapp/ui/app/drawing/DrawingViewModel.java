package com.smallgroup.animationapp.ui.app.drawing;

import android.app.Application;
import android.graphics.Bitmap;
import android.os.Environment;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModel;

import com.arthenica.mobileffmpeg.Config;
import com.arthenica.mobileffmpeg.ExecuteCallback;
import com.arthenica.mobileffmpeg.FFmpeg;
import com.smallgroup.animationapp.domain.model.FileManager;

import java.io.File;
import java.util.ArrayList;

import static com.arthenica.mobileffmpeg.Config.RETURN_CODE_CANCEL;
import static com.arthenica.mobileffmpeg.Config.RETURN_CODE_SUCCESS;

public class DrawingViewModel extends AndroidViewModel {

    private FileManager fileManager;

    public DrawingViewModel(@NonNull Application application) {
        super(application);
        fileManager = new FileManager(
                getApplication()
        );
    }

    public void save(ArrayList<Bitmap> bitmapArrayList) {
        //TODO
        //Save in BD

        for (int i = 0; i < bitmapArrayList.size(); i++) {
            fileManager.saveBitmap(
                    bitmapArrayList.get(i),
                    "img00" + i
            );
        }
    }

    public void buildVideo(String title, int fps) {

        String dirPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Anime";

        String pictures = dirPath + File.separator + "img%02d.png";
        String out = fileManager.createVideo(title);
        String[] cmd= new String[]{"-framerate", String.valueOf(fps), "-i", pictures, "-vf", "scale=720:720", "-y", out};

        long executionId = FFmpeg.executeAsync(cmd, new ExecuteCallback() {

            @Override
            public void apply(final long executionId, final int returnCode) {
                if (returnCode == RETURN_CODE_SUCCESS) {
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
