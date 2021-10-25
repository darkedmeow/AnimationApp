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
import com.smallgroup.animationapp.db.UserProject;
import com.smallgroup.animationapp.domain.model.Frame;
import com.smallgroup.animationapp.repository.RoomRepository;
import com.smallgroup.animationapp.utils.FileManager;

import java.io.File;
import java.util.ArrayList;

import static com.arthenica.mobileffmpeg.Config.RETURN_CODE_CANCEL;
import static com.arthenica.mobileffmpeg.Config.RETURN_CODE_SUCCESS;

public class DrawingViewModel extends AndroidViewModel {

    private final FileManager fileManager;
    private RoomRepository roomRepository;
    MutableLiveData<Boolean> isComplete = new MutableLiveData<>();

    public DrawingViewModel(@NonNull Application application) {
        super(application);
        fileManager = new FileManager(getApplication());
        roomRepository = new RoomRepository(application);
    }


    public void save(ArrayList<Bitmap> bitmapArrayList, ArrayList<Frame> frames) {
        //TODO
        //Save in BD
//        UserProject project = new UserProject("hello", 10, frames);
//        roomRepository.insert(project);

        for (int i = 1; i <= bitmapArrayList.size(); i++) {
            fileManager.saveBitmap(
                    bitmapArrayList.get(i-1),
                    String.format("img%03d", i)
            );
        }
    }

    public void buildVideo(String title, String fps) {

        //path of app folder
        String dirPath = fileManager.getVideoFolderPath();
        //pattern of path img
        String pictures = dirPath + File.separator + "img%03d.png";
        //path of video
        String out = fileManager.createVideo(title);

        String[] cmd= new String[]{"-framerate", fps, "-i", pictures, "-vcodec", "mpeg4", "-s", "720x480", "-y", out};


        //TODO
        //Create a class for building project from list of bitmap
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
