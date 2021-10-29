package com.smallgroup.animationapp.ui.app.menu;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.smallgroup.animationapp.R;
import com.smallgroup.animationapp.databinding.ActivityMenuBinding;
import com.smallgroup.animationapp.domain.model.ProjectPreview;
import com.smallgroup.animationapp.domain.model.User;
import com.smallgroup.animationapp.ui.BaseActivity;
import com.smallgroup.animationapp.ui.app.creating.ProjectCreationActivity;

import java.util.ArrayList;

public class MenuActivity extends BaseActivity {

    private static final String USER = "CURRENT USER";
    private GoogleSignInClient googleSignInClient;

    private MenuViewModel menuViewModel;
    private ActivityMenuBinding binding;

    private RecyclerViewAdapter adapter;
    private MenuActivity context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        initBinding();
        initMenuViewModel();
        initListeners();

        context = this;
        menuViewModel.getProjectsLiveData().observeForever(new Observer<ArrayList<ProjectPreview>>() {
            @Override
            public void onChanged(ArrayList<ProjectPreview> projectPreviews) {
                adapter = new RecyclerViewAdapter(context, projectPreviews);
                binding.rvProjects.setLayoutManager(new GridLayoutManager(context, 3));
                binding.rvProjects.setAdapter(adapter);
            }
        });


        User user = getUserFromIntent();
        initGoogleSignInClient();
    }


    private void initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_menu);
    }

    private void initMenuViewModel() {
        menuViewModel = ViewModelProviders.of(this).get(MenuViewModel.class);
        if (menuViewModel != null) {
            binding.setViewModel(menuViewModel);
        }
    }

    private void initListeners() {
        binding.addPprojectBtn.setOnClickListener(v -> {
            Intent intent = new Intent(this, ProjectCreationActivity.class);
            startActivity(intent);
        });

        binding.settingBtn.setOnClickListener(v -> {
            showMessage("Setting");
        });
    }


    private void initGoogleSignInClient() {
        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions
                .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .build();
        googleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions);
    }

    private User getUserFromIntent() {
        return (User) getIntent().getSerializableExtra(USER);
    }
}