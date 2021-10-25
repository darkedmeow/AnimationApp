package com.smallgroup.animationapp.ui.app;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.smallgroup.animationapp.App;
import com.smallgroup.animationapp.db.AppDatabase;
import com.smallgroup.animationapp.db.TitleProject;
import com.smallgroup.animationapp.domain.model.ProjectPreview;
import com.smallgroup.animationapp.repository.RoomRepository;

import java.util.ArrayList;
import java.util.List;

public class MenuViewModel extends AndroidViewModel {

    MutableLiveData<ArrayList<ProjectPreview>> projectsLiveData;
    private ArrayList<ProjectPreview> projectsList;
    private AppDatabase db;
    private RoomRepository roomRepository;


    public MenuViewModel(@NonNull Application application) {
        super(application);
        roomRepository = new RoomRepository(application);
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

    private void showLogInObserver(String msg) {
        Log.d("LOG", msg);
    }

    private void populateList() {

        MutableLiveData<List<TitleProject>> titleProjectMutableLiveData = roomRepository.getAllProjectUidAndTitle();
        titleProjectMutableLiveData.observeForever(new Observer<List<TitleProject>>() {
            @Override
            public void onChanged(List<TitleProject> titleProjects) {
                titleProjectMutableLiveData.getValue().stream()
                        .map(obj -> new ProjectPreview(obj.title))
                        .forEach(obj -> projectsList.add(obj));
                projectsLiveData.setValue(projectsList);
            }
        });


    }

}
