package com.smallgroup.animationapp.ui.app.drawing;

import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ProgressBar;

import com.smallgroup.animationapp.R;
import com.smallgroup.animationapp.databinding.ActivityDrawingBinding;
import com.smallgroup.animationapp.domain.model.ProjectSetting;
import com.smallgroup.animationapp.ui.BaseActivity;
import com.smallgroup.animationapp.ui.app.video.VideoPlayerActivity;

import java.util.ArrayList;


public class DrawingActivity extends BaseActivity {

    private ActivityDrawingBinding binding;
    private DrawingViewModel drawingViewModel;


    private ProjectSetting setting;
    private FrameRVAdapter adapter;
    private FrameRVAdapter.OnAddFrameClickListener onAddFrameClickListener;

    private Bundle bundle;

    private boolean isShowTools;

    private ArrayList<Bitmap> tempList;

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
        getSettingFromBundle();
        prepareAdapter();

        isShowTools = true;

    }

    private void prepareAdapter() {

        tempList = new ArrayList<Bitmap>();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);

        binding.framesList.setNestedScrollingEnabled(false);
        binding.framesList.setLayoutManager(
                linearLayoutManager
        );
        adapter = new FrameRVAdapter(
                this,
                tempList,
                onAddFrameClickListener
        );
        binding.framesList.setAdapter(adapter);

    }

    private void getSettingFromBundle() {
        bundle = getIntent().getExtras();

        if (bundle != null) {
            setting = (ProjectSetting) bundle.get(ProjectSetting.class.getSimpleName());
            binding.drawingView.setBackgroundColor(setting.getColor());
        }
    }

    private void initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_drawing);
        binding.setLifecycleOwner(this);
    }

    private void initDrawingViewModel() {
        drawingViewModel = ViewModelProviders.of(this).get(DrawingViewModel.class);
        binding.setViewModel(drawingViewModel);
    }

    private void editBarAnim(View chooseView) {
        View view;
        int count = binding.editbar.getChildCount();
        for (int i = 0; i < count; i++) {
            view = binding.editbar.getChildAt(i);
            if (view.getId() == chooseView.getId()){
                view.animate()
                        .scaleX(1.3f)
                        .scaleY(1.3f)
                        .setDuration(100);
            }
            else {
                view.animate()
                        .scaleX(1f)
                        .scaleY(1f)
                        .setDuration(100);
            }
        }
    }

    private void initListeners() {

        //create new method for it
        binding.editbar.getChildAt(0).animate()
                .scaleX(1.3f)
                .scaleY(1.3f)
                .setDuration(200);
        /////////////////////////////////////////////////////

        binding.undo.setOnClickListener(v -> {
            binding.drawingView.undo();
        });

        binding.redo.setOnClickListener(v -> {
            binding.drawingView.redo();

        });

        binding.hideEditbar.setOnClickListener(v ->
        {
            if (isShowTools) {
                binding.editbar.animate()
                        .translationX(-binding.editbar.getWidth())
                        .alpha(0.0f)
                        .setDuration(200);

                binding.framesList.animate()
                        .translationY(binding.editbar.getHeight())
                        .alpha(0.0f)
                        .setDuration(200);

                binding.hideEditbar.setImageResource(R.drawable.icons8_closed_eye_64);
            }
            else {
                binding.editbar.animate()
                        .translationX(0)
                        .alpha(1.0f)
                        .setDuration(200);

                binding.framesList.animate()
                        .translationY(0)
                        .alpha(1.0f)
                        .setDuration(200);
            }
            binding.hideEditbar.setImageResource(R.drawable.icons8_eye_64);
            isShowTools = !isShowTools;
        });
        //saving and video building
        binding.saveProject.setOnClickListener(v -> {
            binding.drawingView.clearAndSaveBitmap();
            binding.progressBar.setVisibility(ProgressBar.VISIBLE);
            drawingViewModel.save(
                    binding.drawingView.getListBitmaps(),
                    binding.drawingView.getFrames()
            );
            drawingViewModel.buildVideo(setting.title, setting.fps);
        });

        binding.erase.setOnClickListener(v -> {
            editBarAnim(v);
            binding.drawingView.turnOnErase();
        });

        binding.brush.setOnClickListener(v -> {
            editBarAnim(v);
            binding.drawingView.setBrush();
        });

        binding.pencil.setOnClickListener(v -> {
            editBarAnim(v);
            binding.drawingView.setPen();
        });

        drawingViewModel.isComplete.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean) {
                    binding.progressBar.setVisibility(ProgressBar.GONE);
                    Intent intent = new Intent(getApplicationContext(), VideoPlayerActivity.class);
                    intent.putExtra(
                            ProjectSetting.class.getSimpleName(),
                            setting
                    );
                    startActivity(intent);
                }
            }
        });

        onAddFrameClickListener = () -> {
            binding.drawingView.clearAndSaveBitmap();
            adapter.updateFrameList(
                    binding.drawingView.getListBitmaps()
            );
            binding.framesList.scrollToPosition(
                    adapter.getItemCount() - 1
            );
        };

    }



}