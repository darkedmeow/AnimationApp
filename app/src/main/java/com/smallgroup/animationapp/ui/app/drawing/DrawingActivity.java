package com.smallgroup.animationapp.ui.app.drawing;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.smallgroup.animationapp.R;
import com.smallgroup.animationapp.databinding.ActivityDrawingBinding;
import com.smallgroup.animationapp.domain.model.ProjectSetting;
import com.smallgroup.animationapp.ui.BaseActivity;
import com.smallgroup.animationapp.ui.app.MenuViewModel;
import com.smallgroup.animationapp.ui.app.video.VideoPlayerActivity;

import java.io.File;


public class DrawingActivity extends BaseActivity {

    private ActivityDrawingBinding binding;
    private DrawingViewModel drawingViewModel;

    private ProjectSetting setting;

    private static final int YOUR_PERMISSION_STATIC_CODE_IDENTIFIER = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                YOUR_PERMISSION_STATIC_CODE_IDENTIFIER);

        initBinding();
        initDrawingViewModel();
        initListeners();

        Bundle bundle = getIntent().getExtras();


        if (bundle != null) {
            setting = (ProjectSetting) bundle.get(ProjectSetting.class.getSimpleName());
            binding.drawingView.setBackgroundColor(setting.getColor());
        }

    }

    private void initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_drawing);
    }

    private void initDrawingViewModel() {
        drawingViewModel = ViewModelProviders.of(this).get(DrawingViewModel.class);
        if (drawingViewModel != null) {
            binding.setViewModel(drawingViewModel);
        }
    }

    private void initListeners() {

        //undo
        binding.undo.setOnClickListener(v -> {
            showMessage("undo");
        });
        //redo
        binding.redo.setOnClickListener(v -> showMessage("redo"));
        //show/hide
        binding.hideEditbar.setOnClickListener(v ->
        {
            if (binding.editbar.getVisibility() == View.VISIBLE) {
                binding.hideEditbar.setImageResource(R.drawable.icons8_closed_eye_64);
                binding.editbar.setVisibility(View.INVISIBLE);
                binding.newFrame.setVisibility(View.GONE);
            }
            else
            {
                binding.hideEditbar.setImageResource(R.drawable.icons8_eye_64);
                binding.editbar.setVisibility(View.VISIBLE);
                binding.newFrame.setVisibility(View.VISIBLE);
            }
        });
        //saving and video building
        binding.saveProject.setOnClickListener(v -> {
            binding.drawingView.clearAndSaveBitmap();
            binding.progressBar.setVisibility(ProgressBar.VISIBLE);
            drawingViewModel.save(
                    binding.drawingView.getListBitmaps()
            );
            drawingViewModel.buildVideo("Hello", setting.fps);
        });
        //new frame
        binding.newFrame.setOnClickListener(v -> {
            binding.drawingView.clearAndSaveBitmap();
        });
        //erase
        binding.erase.setOnClickListener(v -> binding.drawingView.setErase(true));
        //brush
        binding.brush.setOnClickListener(v -> {
            binding.drawingView.setErase(false);
            binding.drawingView.brush();
        });
        //pen
        binding.pencil.setOnClickListener(v -> {
            binding.drawingView.setErase(false);
            binding.drawingView.pen();
        });

        drawingViewModel.isComplete.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean) {
                    binding.progressBar.setVisibility(ProgressBar.GONE);
                    Intent intent = new Intent(getApplicationContext(), VideoPlayerActivity.class);
                    startActivity(intent);
                }
            }
        });

    }



}