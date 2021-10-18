package com.smallgroup.animationapp.ui.app;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.smallgroup.animationapp.App;
import com.smallgroup.animationapp.db.AppDatabase;
import com.smallgroup.animationapp.domain.model.ProjectPreview;

import java.util.ArrayList;

public class MenuViewModel extends AndroidViewModel {

    MutableLiveData<ArrayList<ProjectPreview>> projectsLiveData;
    private ArrayList<ProjectPreview> projectsList;
    private AppDatabase db;


    public MenuViewModel(@NonNull Application application) {
        super(application);
        projectsLiveData = new MutableLiveData<>();
        projectsList = new ArrayList<>();
        db = App.getInstance().getDatabase();
        init();
    }

    public MutableLiveData<ArrayList<ProjectPreview>> getProjectsLiveData() {
        return projectsLiveData;
    }

    private void init() {
        populateList();
        projectsLiveData.setValue(projectsList);
    }

    private void populateList() {
        ProjectPreview project = new ProjectPreview();
        project.setTitle("Мой");
        ProjectPreview blank = new ProjectPreview();
        blank.setTitle("Blank");

        ArrayList<String> projectTitleFromDb = (ArrayList<String>) db.userProjectDao().getAllProjectTitle();
        projectTitleFromDb.forEach(one -> {
            projectsList.add(new ProjectPreview(one));
        });

        projectsList.add(project);
        projectsList.add(blank);

    }

}
