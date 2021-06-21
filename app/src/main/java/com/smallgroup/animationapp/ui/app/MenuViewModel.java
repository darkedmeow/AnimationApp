package com.smallgroup.animationapp.ui.app;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.smallgroup.animationapp.domain.model.ProjectPreview;

import java.util.ArrayList;

public class MenuViewModel extends ViewModel {

    MutableLiveData<ArrayList<ProjectPreview>> projectsLiveData;
    private ArrayList<ProjectPreview> projectsList;

    public MenuViewModel() {
        projectsLiveData = new MutableLiveData<>();
        projectsList = new ArrayList<>();
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
        project.setTitle("Test");

        projectsList.add(project);
        projectsList.add(project);
        projectsList.add(project);
        projectsList.add(project);
        projectsList.add(project);
        projectsList.add(project);
        projectsList.add(project);
        projectsList.add(project);

    }

}
