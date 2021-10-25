package com.smallgroup.animationapp.ui.app.video;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.content.Intent;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.MediaController;

import com.smallgroup.animationapp.R;
import com.smallgroup.animationapp.databinding.ActivityVideoPlayerBinding;
import com.smallgroup.animationapp.domain.model.ProjectSetting;
import com.smallgroup.animationapp.ui.BaseActivity;

public class VideoPlayerActivity extends BaseActivity {

    private Bundle bundle;
    private ProjectSetting setting;

    private Context context;

    private ActivityVideoPlayerBinding binding;
    private VideoPLayerViewModel viewModel;
    private Uri videoUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        initBinding();
        initDrawingViewModel();
        getSettingFromBundle();
        initListeners();

        openVideo();

        context = this;

    }

    private void getSettingFromBundle() {
        bundle = getIntent().getExtras();

        if (bundle != null) {
            setting = (ProjectSetting) bundle.get(ProjectSetting.class.getSimpleName());
        }
    }

    private void openVideo() {
        MediaController mediaController = new MediaController(this);
        binding.video.setMediaController(mediaController);
        mediaController.setMediaPlayer(binding.video);
        viewModel.setTitileVideo(setting.title);
        viewModel.videoUri.observe(this, s -> {
            videoUri = s;
            binding.video.setVideoURI(s);
            binding.video.start();
            Log.d("INFO", s.toString());
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
        binding.share.setOnClickListener(v -> {
            Intent share = new Intent(Intent.ACTION_SEND);
            share.putExtra(Intent.EXTRA_TEXT, "This is my text to send.");
            Log.d("URI", videoUri.toString());
            share.putExtra(Intent.EXTRA_STREAM, videoUri);
            share.setType("video/mp4");

            startActivity(Intent.createChooser(share, "Message"));
        });
    }


}