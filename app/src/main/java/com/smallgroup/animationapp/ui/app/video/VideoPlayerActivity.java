package com.smallgroup.animationapp.ui.app.video;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.MediaController;

import com.smallgroup.animationapp.R;
import com.smallgroup.animationapp.databinding.ActivityVideoPlayerBinding;
import com.smallgroup.animationapp.ui.BaseActivity;

public class VideoPlayerActivity extends BaseActivity {

    private ActivityVideoPlayerBinding binding;
    private VideoPLayerViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        initBinding();
        initDrawingViewModel();
        initListeners();

        openVideo();

    }

    private void openVideo() {
        MediaController mediaController = new MediaController(this);
        binding.video.setMediaController(mediaController);
        mediaController.setMediaPlayer(binding.video);
        viewModel.videoUri.observe(this, new Observer<Uri>() {
            @Override
            public void onChanged(Uri s) {
                binding.video.setVideoURI(s);
                binding.video.start();
                Log.d("INFO", s.toString());
            }
        });
    }

    private void initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_video_player);
    }

    private void initDrawingViewModel() {
        viewModel = ViewModelProviders.of(this).get(VideoPLayerViewModel.class);
        binding.setViewModel(viewModel);
    }

    private void initListeners() {

    }
}