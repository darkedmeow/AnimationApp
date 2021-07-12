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

    private final int VIEW_TYPE_FOOTER = 2;
    private final int VIEW_TYPE_FRAME = 1;

    private OnAddFrameClickListener listener;

    interface OnAddFrameClickListener {
        void onAddFrame();
    }

    public FrameRVAdapter(Activity context, ArrayList<Bitmap> frames, OnAddFrameClickListener listener) {
        this.context = context;
        this.frames = frames;
        this.listener = listener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (viewType == VIEW_TYPE_FRAME) {

            View rootView = LayoutInflater.from(context).inflate(R.layout.item_frame, parent, false);
            return new FrameRVAdapter.ViewHolder(rootView);

        }
        else {
            View rootView = LayoutInflater.from(context).inflate(R.layout.item_new_frame, parent, false);
            return new FrameRVAdapter.ViewHolder(rootView);
        }

//        View rootView = LayoutInflater.from(context).inflate(R.layout.item_frame, parent, false);
//        return new FrameRVAdapter.ViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if (position == frames.size()) {
            holder.itemView.setOnClickListener(v -> {
                listener.onAddFrame();
                notifyDataSetChanged();
            });
        }
        else {
            Bitmap frame = frames.get(position);
            FrameRVAdapter.ViewHolder viewHolder = (FrameRVAdapter.ViewHolder) holder;

            viewHolder.title.setText("" + String.valueOf(position + 1));
            viewHolder.iconImg.setImageBitmap(frame);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return (position == frames.size()) ? VIEW_TYPE_FOOTER : VIEW_TYPE_FRAME;
    }

    @Override
    public int getItemCount() {
        return frames.size() + 1;
    }

    public void updateFrameList(ArrayList<Bitmap> list) {
        frames.clear();
        frames.addAll(list);
        notifyDataSetChanged();
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

    class FooterViewGolder extends RecyclerView.ViewHolder {

        ImageView addFrameImg;


        public FooterViewGolder(@NonNull View itemView) {
            super(itemView);
            addFrameImg = itemView.findViewById(R.id.new_frame_image);
        }
    }

}
