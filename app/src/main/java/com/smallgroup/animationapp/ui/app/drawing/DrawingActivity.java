package com.smallgroup.animationapp.ui.app.drawing;

import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
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

    private MutableLiveData<Boolean> isShow = new MutableLiveData<>();

    private ProjectSetting setting;
    private FrameRVAdapter adapter;
    private FrameRVAdapter.OnAddFrameClickListener onAddFrameClickListener;

    private Bundle bundle;

    private ArrayList<Bitmap> testList;

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
        getSetting();
        initAdapters();

        isShow.setValue(true);
        binding.setIsShow(isShow);

    }

    private void initAdapters() {

        testList = new ArrayList<Bitmap>();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);

        binding.framesList.setNestedScrollingEnabled(false);
        binding.framesList.setLayoutManager(
                linearLayoutManager
        );
        adapter = new FrameRVAdapter(
                this,
                testList,
                onAddFrameClickListener
        );
        binding.framesList.setAdapter(adapter);

    }

    private void getSetting() {
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
        if (drawingViewModel != null) {
            binding.setViewModel(drawingViewModel);
        }
    }

    private void initListeners() {

        //undo
        binding.undo.setOnClickListener(v -> {
            //showMessage("undo");
            binding.drawingView.undo();
        });
        //redo
        binding.redo.setOnClickListener(v -> {
            //showMessage("redo");
            binding.drawingView.redo();

        });
        //show/hide
        binding.hideEditbar.setOnClickListener(v ->
        {
            isShow.setValue(!isShow.getValue());
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
        //erase
        binding.erase.setOnClickListener(v -> binding.drawingView.turnOnErase());
        //brush
        binding.brush.setOnClickListener(v -> {
            binding.drawingView.setBrush();
        });
        //pen
        binding.pencil.setOnClickListener(v -> {
            binding.drawingView.setPen();
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

        onAddFrameClickListener = new FrameRVAdapter.OnAddFrameClickListener() {
            @Override
            public void onAddFrame() {
                binding.drawingView.clearAndSaveBitmap();
                adapter.updateFrameList(
                        binding.drawingView.getListBitmaps()
                );
                binding.framesList.scrollToPosition(
                        adapter.getItemCount() - 1
                );
            }
        };

    }



}