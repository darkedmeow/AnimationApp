package com.smallgroup.animationapp.ui.app;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.graphics.ColorUtils;
import androidx.recyclerview.widget.RecyclerView;

import com.smallgroup.animationapp.R;
import com.smallgroup.animationapp.domain.model.ProjectPreview;

import java.util.ArrayList;

public class FonRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Activity context;
    ArrayList<Integer> colors;

    public FonRecyclerViewAdapter(Activity context, ArrayList<Integer> colors) {
        this.context = context;
        this.colors = colors;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(context).inflate(R.layout.item_fon, parent, false);
        return new RecyclerViewViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        int color = colors.get(position);
        RecyclerViewViewHolder viewViewHolder = (RecyclerViewViewHolder) holder;

        viewViewHolder.color.setBackgroundColor(color);

    }

    @Override
    public int getItemCount() {
        return colors.size();
    }

    class RecyclerViewViewHolder extends RecyclerView.ViewHolder {

        ImageView color;

        public RecyclerViewViewHolder(@NonNull View itemView) {
            super(itemView);
            color = itemView.findViewById(R.id.color);
        }
    }
}
