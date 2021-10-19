package com.smallgroup.animationapp.repository;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;
import androidx.work.WorkRequest;

import com.smallgroup.animationapp.App;
import com.smallgroup.animationapp.db.AppDatabase;
import com.smallgroup.animationapp.db.TitleProject;
import com.smallgroup.animationapp.db.UserProject;
import com.smallgroup.animationapp.domain.model.Frame;
import com.smallgroup.animationapp.repository.worker.GetAllProjectWorker;
import com.smallgroup.animationapp.repository.worker.InsertWorker;
import com.smallgroup.animationapp.repository.worker.SelectWorker;
import com.smallgroup.animationapp.repository.worker.UpdateWorker;
import com.smallgroup.animationapp.utils.Converter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RoomRepository {

    public static final String KEY_TITLE = "KEY_TITLE";
    public static final String KEY_FPS = "KEY_FPS";
    public static final String KEY_ID = "KEY_ID";
    public static final String KEY_FRAMES = "KEY_FRAMES";

    private Application context;

    public RoomRepository(Application application) {
        this.context = application;
    }

    public MutableLiveData<Long> insert(UserProject project) {
        MutableLiveData<Long> id = new MutableLiveData<>();
        WorkRequest insertOneTimeRequest = new OneTimeWorkRequest.Builder(InsertWorker.class)
                .setInputData(convertUserProjectToData(project))
                .build();
        WorkManager.getInstance(context).enqueue(insertOneTimeRequest);
        WorkManager.getInstance(context)
                .getWorkInfoByIdLiveData(insertOneTimeRequest.getId())
                .observeForever(new Observer<WorkInfo>() {
                    @Override
                    public void onChanged(WorkInfo info) {
                        if (info != null && info.getState().isFinished()) {
                            long myResult = info.getOutputData().getLong(InsertWorker.RESULT,
                                    0);
                            id.postValue(myResult);
                        }
                    }
                });
        return id;
    }

    public void update(UserProject project) {
        WorkRequest updateRequest = new OneTimeWorkRequest.Builder(UpdateWorker.class)
                .setInputData(convertUserProjectToData(project))
                .build();
        WorkManager.getInstance(context).enqueue(updateRequest);
    }

//    public MutableLiveData<List<Frame>> getListFrameById(int id) {
//        MutableLiveData<List<Frame>> framesLiveData = new MutableLiveData<>();
//        Data data = new Data.Builder().putInt(KEY_ID, id).build();
//        WorkRequest getListFrames = new OneTimeWorkRequest.Builder(SelectWorker.class)
//                .setInputData(data)
//                .build();
//        WorkManager.getInstance(context).enqueue(getListFrames);
//
//        WorkManager.getInstance(context).getWorkInfoByIdLiveData(getListFrames.getId())
//                .observe((LifecycleOwner) this, new Observer<WorkInfo>() {
//                    @Override
//                    public void onChanged(WorkInfo info) {
//                        if (info != null && info.getState().isFinished()) {
//                            byte[] frames = info.getOutputData().getByteArray(KEY_FRAMES);
//                            framesLiveData.postValue(Converter.byteToList(frames));
//                        }
//                    }
//                });
//        return framesLiveData;
//    }

    public MutableLiveData<List<TitleProject>> getAllProjectUidAndTitle() {
        MutableLiveData<List<TitleProject>> titleProjectList = new MutableLiveData<>();

        WorkRequest getAllTitleProject = new OneTimeWorkRequest.Builder(GetAllProjectWorker.class).build();
        WorkManager.getInstance(context).enqueue(getAllTitleProject);

        WorkManager.getInstance(context).getWorkInfoByIdLiveData(getAllTitleProject.getId())
                .observeForever(new Observer<WorkInfo>() {
                    @Override
                    public void onChanged(WorkInfo info) {
                        if (info != null && info.getState().isFinished()) {
                            int[] uid = info.getOutputData().getIntArray(GetAllProjectWorker.UIDS);
                            String[] titile = info.getOutputData().getStringArray(GetAllProjectWorker.TITLES);

                            //TODO ПЕРЕПИСАТЬ ЭТОТ УЖАС
                            try {
                                List<TitleProject> list = new ArrayList<>();
                                if (titile != null) {
                                    for (int i = 0; i < titile.length; i++) {
                                        list.add(new TitleProject(titile[i], uid[i]));
                                    }
                                    titleProjectList.postValue(list);
                                }
                            }
                            catch (Exception exception) {
                                titleProjectList.postValue(new ArrayList<>());
                            }
                        }
                    }
                });
        return titleProjectList;

    }

    public static Data convertUserProjectToData(UserProject project) {
        Data data = new Data.Builder()
                .putString(KEY_TITLE, project.title)
                .putInt(KEY_FPS, project.fps)
                .putInt(KEY_ID, project.uid)
                .putByteArray(KEY_FRAMES, Converter.listToByte(project.frames))
                .build();
        return data;
    }

    public static UserProject convertDataToUserProject(Data data) {
        String title = data.getString(KEY_TITLE);
        int fps = data.getInt(KEY_FPS, 10);
        int uid = data.getInt(KEY_ID, 0);
        List<Frame> frames = Converter.byteToList(data.getByteArray(KEY_FRAMES));

        return new UserProject(uid, title, fps, frames);
    }


}
