package com.smallgroup.animationapp.repository.worker;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.smallgroup.animationapp.App;
import com.smallgroup.animationapp.db.UserProject;
import com.smallgroup.animationapp.repository.RoomRepository;

public class InsertWorker extends Worker {

    public static final String RESULT = "RESULT_LONG_ID";

    public InsertWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        UserProject userProject = RoomRepository.convertDataToUserProject(getInputData());
        long id = App.getInstance().getDatabase().userProjectDao().insert(userProject);
        Data output = new Data.Builder()
                                .putLong(RESULT, id)
                                .build();
        return Result.success(output);
    }
}
