package com.smallgroup.animationapp.ui.app.drawing;

import android.app.Activity;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.smallgroup.animationapp.R;

import java.util.ArrayList;

public class FrameRVAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Activity context;
    ArrayList<Bitmap> frames;

    public FrameRVAdapter(Activity context, ArrayList<Bitmap> frames) {
        this.context = context;
        this.frames = frames;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(context).inflate(R.layout.item_frame, parent, false);
        return new FrameRVAdapter.ViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        Bitmap frame = frames.get(position);
        FrameRVAdapter.ViewHolder viewViewHolder = (FrameRVAdapter.ViewHolder) holder;

        viewViewHolder.title.setText("" + String.valueOf(position + 1));
        viewViewHolder.iconImg.setImageBitmap(frame);

    }

    @Override
    public int getItemCount() {
        return frames.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView title;
        ImageView iconImg;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.frame_num);
            iconImg = itemView.findViewById(R.id.frame_image);
        }
    }

}
