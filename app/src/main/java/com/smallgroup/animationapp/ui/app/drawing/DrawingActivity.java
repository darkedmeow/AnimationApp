package com.smallgroup.animationapp.ui.app.drawing;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import android.Manifest;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.smallgroup.animationapp.R;
import com.smallgroup.animationapp.databinding.ActivityDrawingBinding;
import com.smallgroup.animationapp.ui.BaseActivity;
import com.smallgroup.animationapp.ui.app.MenuViewModel;

import java.io.File;


public class DrawingActivity extends BaseActivity {

    private ActivityDrawingBinding binding;
    private DrawingViewModel drawingViewModel;

    private static final int YOUR_PERMISSION_STATIC_CODE_IDENTIFIER = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        initBinding();
        initDrawingViewModel();
        initListeners();

        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                YOUR_PERMISSION_STATIC_CODE_IDENTIFIER);

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

        binding.undo.setOnClickListener(v -> {
            String pictures =
                    Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsolutePath()+
                    File.separator + "img%02d.png";
            Log.i("PATH", pictures);
        });
        binding.redo.setOnClickListener(v -> showMessage("redo"));
        binding.hideEditbar.setOnClickListener(v ->
        {
            if (binding.toolbar.getVisibility() == View.VISIBLE)
                binding.toolbar.setVisibility(View.INVISIBLE);
            else
                binding.toolbar.setVisibility(View.VISIBLE);
        });
        binding.saveProject.setOnClickListener(v -> {
            binding.drawingView.clearAndSaveBitmap();
            drawingViewModel.save(
                    binding.drawingView.getListBitmaps()
            );
            drawingViewModel.buildVideo("Hello", 3);
        });
        binding.newFrame.setOnClickListener(v -> {
            binding.drawingView.clearAndSaveBitmap();
        });

    }

}