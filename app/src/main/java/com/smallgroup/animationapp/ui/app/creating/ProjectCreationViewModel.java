package com.smallgroup.animationapp.ui.app.creating;

import android.app.Activity;
import android.util.Log;

import androidx.databinding.Bindable;
import androidx.databinding.ObservableField;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.smallgroup.animationapp.domain.model.ProjectSetting;

import java.util.ArrayList;
import java.util.List;

public class ProjectCreationViewModel extends ViewModel {

    MutableLiveData<ArrayList<Integer>> colorsLiveData;
    private ProjectSetting setting;
    public ObservableField<ProjectSetting> myset = new ObservableField<>(new ProjectSetting("project", "10"));

    public ProjectCreationViewModel() {
        colorsLiveData = new MutableLiveData<>();
        init();
    }

    private void init() {

    }

    public void setColor(int color) {
        myset.get().setColor(color);
    }

    public String info() {
        return myset.get().info();
    }

    public void populateColors(int[] ints){
        ArrayList<Integer> colorsByInt = new ArrayList<Integer>(ints.length);
        for (int i : ints)
        {
            colorsByInt.add(i);
        }
        colorsLiveData.setValue(colorsByInt);
    }

}
