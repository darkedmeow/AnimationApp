package com.smallgroup.animationapp.ui.app.creating;

import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.smallgroup.animationapp.R;

import java.util.ArrayList;

public class FonRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Activity context;
    ArrayList<Integer> colors;
    private int selectedPosition = 0;

    private final OnColorClickListener onColorClickListener;

    interface OnColorClickListener {
        void onColorSelect(int color, int position);
    }

    public FonRecyclerViewAdapter(Activity context, ArrayList<Integer> colors, OnColorClickListener onColorClickListener) {
        this.context = context;
        this.colors = colors;
        this.onColorClickListener = onColorClickListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(context).inflate(R.layout.item_fon, parent, false);
        return new RecyclerViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        int color = colors.get(position);
        RecyclerViewHolder viewHolder = (RecyclerViewHolder) holder;

        viewHolder.colorView.setBackgroundColor(color);

        if (selectedPosition == position) {
            viewHolder.itemView.setSelected(true);
            viewHolder.selected.setVisibility(View.VISIBLE);
        }
        else {
            viewHolder.itemView.setSelected(false);
            viewHolder.selected.setVisibility(View.INVISIBLE);
        }

        viewHolder.colorView.setOnClickListener(v -> {
            if (selectedPosition >= 0) {
                onColorClickListener.onColorSelect(color, position);
                notifyItemChanged(selectedPosition);
            }
            selectedPosition = viewHolder.getAdapterPosition();
            notifyItemChanged(selectedPosition);
        });

    }

    @Override
    public int getItemCount() {
        return colors.size();
    }

    class RecyclerViewHolder extends RecyclerView.ViewHolder {

        ImageView colorView;
        ImageView selected;

        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            colorView = itemView.findViewById(R.id.color);
            selected = itemView.findViewById(R.id.selected);
        }
    }
}
