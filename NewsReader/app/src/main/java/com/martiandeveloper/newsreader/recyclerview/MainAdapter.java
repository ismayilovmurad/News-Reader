package com.martiandeveloper.newsreader.recyclerview;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.martiandeveloper.newsreader.R;
import com.martiandeveloper.newsreader.activity.DetailActivity;
import com.martiandeveloper.newsreader.model.Article;
import com.martiandeveloper.newsreader.utils.DateUtils;

import java.util.List;

public class MainAdapter extends RecyclerView.Adapter<MainViewHolder> {

    private final Context mContext;
    private final List<Article> articleList;

    private final String LOG;

    public MainAdapter(Context mContext, List<Article> articleList) {
        this.mContext = mContext;
        this.articleList = articleList;

        LOG = "MartianDeveloper";
    }

    @NonNull
    @Override
    public MainViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(mContext)
                .inflate(R.layout.recycler_item_main, parent, false);

        return new MainViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MainViewHolder holder, int position) {

        if (articleList != null) {
            if (articleList.size() > 0) {

                try {

                    Glide.with(mContext)
                            .load(articleList.get(position).getUrlToImage())
                            .diskCacheStrategy(DiskCacheStrategy.DATA)
                            .centerCrop()
                            .placeholder(R.drawable.image_placeholder)
                            .priority(Priority.IMMEDIATE)
                            .into(holder.posterIV);

                    holder.titleTV.setText(articleList.get(position).getTitle());
                    holder.timeTV.setText(DateUtils.formatNewsApiDate(articleList.get(position).getPublishedAt()));
                    holder.contentTV.setText(articleList.get(position).getContent());

                    holder.itemView.setOnClickListener(v -> goToTheDetail(position));

                } catch (Exception e) {
                    if (e.getLocalizedMessage() != null) {
                        Log.d(LOG, "" + e.getLocalizedMessage());
                    } else {
                        Log.d(LOG, "");
                    }
                }
            }
        }
    }

    private void goToTheDetail(int position) {
        Intent intent = new Intent(mContext, DetailActivity.class);
        intent.putExtra("webUrl", articleList.get(position).getUrl());
        intent.putExtra("title", articleList.get(position).getTitle());
        mContext.startActivity(intent);
    }

    @Override
    public void onViewRecycled(@NonNull MainViewHolder holder) {
        super.onViewRecycled(holder);

        Glide.with(mContext).clear(holder.posterIV);
    }

    @Override
    public int getItemCount() {
        return articleList.size();
    }
}
