package com.smallgroup.animationapp.ui.app;

import android.app.Activity;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

public class ProjectCreationViewModel extends ViewModel {

    MutableLiveData<ArrayList<Integer>> colorsLiveData;

    public ProjectCreationViewModel() {
        colorsLiveData = new MutableLiveData<>();
        init();
    }

    private void init() {

    }

    public void populateList(int[] ints){
        ArrayList<Integer> intList = new ArrayList<Integer>(ints.length);
        for (int i : ints)
        {
            Log.d("RES", String.valueOf(i));
            intList.add(i);
        }
        colorsLiveData.setValue(intList);
    }

}
