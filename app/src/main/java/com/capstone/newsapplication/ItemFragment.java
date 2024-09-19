package com.capstone.newsapplication;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.capstone.newsapplication.DataModels.News;
import com.capstone.newsapplication.DataModels.NewsData;

import java.util.List;

public class ItemFragment extends Fragment {

    private OnNewsItemClickListener callback;

    public ItemFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_item_list, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);

        // Use NewsData to get the list of news
        List<News> newsList = NewsData.getNewsList();

        if (recyclerView != null) {
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            recyclerView.setAdapter(new MyItemRecyclerViewAdapter(newsList, callback)); // Pass the newsList
        }

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            callback = (OnNewsItemClickListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement OnNewsItemClickListener");
        }
    }

    public interface OnNewsItemClickListener {
        void onNewsItemClicked(String newsTitle, String newsDetails, int newsImageResId);
    }
}
