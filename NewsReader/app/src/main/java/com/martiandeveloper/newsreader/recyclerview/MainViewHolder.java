package com.martiandeveloper.newsreader.recyclerview;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.martiandeveloper.newsreader.R;

class MainViewHolder extends RecyclerView.ViewHolder {

    final ImageView posterIV;
    final TextView titleTV;
    final TextView timeTV;
    final TextView contentTV;

    MainViewHolder(@NonNull View itemView) {
        super(itemView);

        posterIV = itemView.findViewById(R.id.posterIV);
        titleTV = itemView.findViewById(R.id.titleTV);
        timeTV = itemView.findViewById(R.id.timeTV);
        contentTV = itemView.findViewById(R.id.contentTV);
    }
}
