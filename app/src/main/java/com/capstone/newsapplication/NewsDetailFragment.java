package com.capstone.newsapplication;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class NewsDetailFragment extends Fragment {

    private static final String ARG_TITLE = "newsTitle";
    private static final String ARG_DETAILS = "newsDetails";
    private static final String ARG_IMAGE = "newsImage";
    private String newsTitle;
    private String newsDetails;
    private int newsImageResId;

    public NewsDetailFragment() {
        // Required empty public constructor
    }

    // Update this method to accept three arguments: title, details, and imageResId
    public static NewsDetailFragment newInstance(String title, String details, int imageResId) {
        NewsDetailFragment fragment = new NewsDetailFragment();
        Bundle args = new Bundle();
        args.putString(ARG_TITLE, title);
        args.putString(ARG_DETAILS, details);
        args.putInt(ARG_IMAGE, imageResId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            newsTitle = getArguments().getString(ARG_TITLE);
            newsDetails = getArguments().getString(ARG_DETAILS);
            newsImageResId = getArguments().getInt(ARG_IMAGE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_news_detail, container, false);

        // Set up the views to display the news title, details, and image
        TextView titleView = view.findViewById(R.id.news_title);
        TextView detailsView = view.findViewById(R.id.news_details);
        ImageView imageView = view.findViewById(R.id.news_image);

        titleView.setText(newsTitle);
        detailsView.setText(newsDetails);
        imageView.setImageResource(newsImageResId);

        return view;
    }
}
