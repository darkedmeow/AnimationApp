package com.smallgroup.animationapp.repository.worker;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.smallgroup.animationapp.App;
import com.smallgroup.animationapp.db.TitleProject;

import java.util.List;

public class GetAllProjectWorker extends Worker {

    public static final String UIDS = "UIDS";
    public static final String TITLES = "TITLES";

    public GetAllProjectWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        try {
            List<TitleProject> titleProjectArrayList = App.getInstance()
                    .getDatabase()
                    .userProjectDao()
                    .getAllProjectUidAndTitle();

            String[] titles = titleProjectArrayList.stream().map(el -> el.title).toArray(String[]::new);
            int[] uids = titleProjectArrayList.stream().mapToInt(el -> el.uid).toArray();

            Data data = new Data.Builder()
                    .putStringArray(TITLES, titles)
                    .putIntArray(UIDS, uids)
                    .build();

            return Result.success(data);
        }
        catch (Exception ex) {
            return Result.failure();
        }
    }
}
