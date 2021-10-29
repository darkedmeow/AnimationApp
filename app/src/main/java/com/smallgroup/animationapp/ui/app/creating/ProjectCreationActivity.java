package com.smallgroup.animationapp.ui.app.creating;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;

import com.smallgroup.animationapp.R;
import com.smallgroup.animationapp.databinding.ActivityProjectCreationBinding;
import com.smallgroup.animationapp.domain.model.ProjectSetting;
import com.smallgroup.animationapp.ui.BaseActivity;
import com.smallgroup.animationapp.ui.app.drawing.DrawingActivity;

import java.util.ArrayList;

public class ProjectCreationActivity extends BaseActivity {

    private ProjectCreationViewModel projectCreationViewModel;
    private ActivityProjectCreationBinding binding;

    private FonRecyclerViewAdapter adapter;
    private Activity context;
    private FonRecyclerViewAdapter.OnColorClickListener onColorClickListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        context = this;

        initBinding();
        initViewModel();
        initListeners();

        projectCreationViewModel.populateColors(
                this.getResources().getIntArray(R.array.colors)
        );

        projectCreationViewModel.colorsLiveData.observe(this, new Observer<ArrayList<Integer>>() {
            @Override
            public void onChanged(ArrayList<Integer> integers) {
                adapter = new FonRecyclerViewAdapter(context, integers, onColorClickListener);
                //take out
                binding.rvFons.setLayoutManager(new GridLayoutManager(context, 3));
                binding.rvFons.setAdapter(adapter);
            }
        });



    }

    private void initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_project_creation);
        binding.setLifecycleOwner(this);

    }

    private void initViewModel() {
        projectCreationViewModel = ViewModelProviders.of(this).get(ProjectCreationViewModel.class);
        binding.setViewModel(projectCreationViewModel);
    }

    private void initListeners() {
        binding.createBtn.setOnClickListener(v -> {
            Intent intent = new Intent(this, DrawingActivity.class);
            intent.putExtra(
                    ProjectSetting.class.getSimpleName(),
                    projectCreationViewModel.myset.get()
            );
            startActivity(intent);
//            showMessage(projectCreationViewModel.info());
        });


        onColorClickListener = (color, position) -> projectCreationViewModel.setColor(color);
    }
}