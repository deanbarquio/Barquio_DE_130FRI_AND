package com.capstone.newsapplication;

import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.capstone.newsapplication.DataModels.News;
import java.util.List;

public class MyItemRecyclerViewAdapter extends RecyclerView.Adapter<MyItemRecyclerViewAdapter.ViewHolder> {

    private final List<News> mNewsList;
    private final ItemFragment.OnNewsItemClickListener mListener;

    public MyItemRecyclerViewAdapter(List<News> newsList, ItemFragment.OnNewsItemClickListener listener) {
        mNewsList = newsList;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        News news = mNewsList.get(position);
        holder.mTitleView.setText(news.getTitle());
        holder.mImageView.setImageResource(news.getImageResId());

        holder.itemView.setOnClickListener(v -> {
            if (mListener != null) {
                mListener.onNewsItemClicked(news.getTitle(), news.getDetails(), news.getImageResId());
            }
        });
    }

    // The missing getItemCount method
    @Override
    public int getItemCount() {
        return mNewsList.size();  // Return the size of the news list
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView mTitleView;
        public final ImageView mImageView;

        public ViewHolder(View view) {
            super(view);
            mTitleView = view.findViewById(R.id.news_title);
            mImageView = view.findViewById(R.id.news_image_thumbnail);  // Correct ID used here
        }
    }
}
