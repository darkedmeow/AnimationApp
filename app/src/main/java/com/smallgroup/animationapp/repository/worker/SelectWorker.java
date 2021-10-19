package com.smallgroup.animationapp.repository.worker;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.smallgroup.animationapp.App;
import com.smallgroup.animationapp.domain.model.Frame;
import com.smallgroup.animationapp.repository.RoomRepository;
import com.smallgroup.animationapp.utils.Converter;

import java.util.List;

public class SelectWorker extends Worker {

    public SelectWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
//        try {
//            int id = getInputData().getInt(RoomRepository.KEY_ID, 0);
//            List<Frame> frameList = App.getInstance().getDatabase().userProjectDao().getListFrameById(id);
//            byte[] frames = Converter.listToByte(frameList);
//            Data data = new Data.Builder().putByteArray(RoomRepository.KEY_FRAMES ,frames).build();
//            return Result.success(data);
//        }
//        catch (Exception ex) {
            return Result.failure();
//        }
    }
}
