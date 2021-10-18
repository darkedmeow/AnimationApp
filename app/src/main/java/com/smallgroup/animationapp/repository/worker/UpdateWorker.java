package com.smallgroup.animationapp.repository.worker;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.smallgroup.animationapp.App;
import com.smallgroup.animationapp.db.UserProject;
import com.smallgroup.animationapp.repository.RoomRepository;

public class UpdateWorker extends Worker {

    public UpdateWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        Data project = getInputData();
        try {
            App.getInstance().getDatabase().userProjectDao().update(RoomRepository.convertDataToUserProject(project));
            return Result.success();
        }
        catch (Exception ex) {
            return Result.failure();
        }
    }
}
