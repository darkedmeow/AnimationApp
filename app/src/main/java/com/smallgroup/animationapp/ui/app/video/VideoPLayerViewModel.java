package com.smallgroup.animationapp.ui.app.video;

import android.app.Application;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.smallgroup.animationapp.domain.model.FileManager;

public class VideoPLayerViewModel extends AndroidViewModel {

    MutableLiveData<Uri> videoUri = new MutableLiveData<>();
    private final FileManager fileManager;


    public VideoPLayerViewModel(@NonNull Application application) {
        super(application);

        fileManager = new FileManager(application);


        videoUri.setValue(Uri.parse(
                fileManager.getVideoFolderPath() + "/Hello.mp4"
        ));
    }
}
