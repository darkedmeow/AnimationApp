package com.smallgroup.animationapp.ui.app;

import android.app.Activity;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.smallgroup.animationapp.domain.model.ProjectSetting;

import java.util.ArrayList;
import java.util.List;

public class ProjectCreationViewModel extends ViewModel {

    MutableLiveData<ArrayList<Integer>> colorsLiveData;
    private ProjectSetting projectSetting;
    public MutableLiveData<ProjectSetting> setting = new MutableLiveData<>();

    public ProjectCreationViewModel() {
        colorsLiveData = new MutableLiveData<>();
        init();
    }

    private void init() {
        projectSetting = new ProjectSetting("hello", 10);
        setting.setValue(projectSetting);
    }

    public void setColor(int color) {
        projectSetting.setColor(color);
    }

    public String info() {
        return projectSetting.info();
    }

    public void populateList(int[] ints){
        ArrayList<Integer> intList = new ArrayList<Integer>(ints.length);
        for (int i : ints)
        {
            //Log.d("RES", String.valueOf(i));
            intList.add(i);
        }
        colorsLiveData.setValue(intList);
    }

}
