package com.smallgroup.animationapp.ui.app;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.smallgroup.animationapp.R;
import com.smallgroup.animationapp.domain.model.ProjectPreview;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Activity context;
    ArrayList<ProjectPreview> projectArrayList;

    public RecyclerViewAdapter(Activity context, ArrayList<ProjectPreview> projects) {
        this.context = context;
        this.projectArrayList = projects;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(context).inflate(R.layout.item_project, parent, false);
        return new RecyclerViewViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        ProjectPreview projectPreview = projectArrayList.get(position);
        RecyclerViewViewHolder viewViewHolder = (RecyclerViewViewHolder) holder;

        viewViewHolder.title.setText(projectPreview.getTitle());
        if (projectPreview.getImgIcon() != null)
            viewViewHolder.iconImg.setImageURI(projectPreview.getImgIcon());
        else {
            viewViewHolder.iconImg.setImageResource(R.drawable.ic_baseline_no_photography_24);
        }

    }

    @Override
    public int getItemCount() {
        return projectArrayList.size();
    }

    class RecyclerViewViewHolder extends RecyclerView.ViewHolder {

        TextView title;
        ImageView iconImg;

        public RecyclerViewViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.project_title);
            iconImg = itemView.findViewById(R.id.intro_img);
        }
    }
}
