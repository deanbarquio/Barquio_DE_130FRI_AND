package com.capstone.newsapplication;

import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity implements ItemFragment.OnNewsItemClickListener {

    private boolean isLandscape;
    private String selectedNewsTitle = null;
    private String selectedNewsDetails = null;
    private int selectedNewsImageResId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("MainActivity", "onCreate called");

        setContentView(R.layout.activity_main);

        // Detect if the device is in landscape mode
        isLandscape = getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;

        // Restore state if available
        if (savedInstanceState != null) {
            selectedNewsTitle = savedInstanceState.getString("selectedNewsTitle");
            selectedNewsDetails = savedInstanceState.getString("selectedNewsDetails");
            selectedNewsImageResId = savedInstanceState.getInt("selectedNewsImageResId", -1);
        }

        // Load the appropriate fragments depending on the orientation and state
        if (savedInstanceState == null) {
            loadInitialFragments();
        } else {
            ensureCorrectFragments();
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        isLandscape = newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE;

        // Reload fragments based on the new orientation
        loadInitialFragments();
        refreshLayout();
    }

    private void loadInitialFragments() {
        if (isLandscape) {
            loadLandscapeFragments();
        } else {
            loadPortraitFragment();
        }
    }

    // Ensures that fragments are correctly loaded for each orientation
    private void ensureCorrectFragments() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (isLandscape) {
            if (fragmentManager.findFragmentById(R.id.fragment_list_container) == null
                    || fragmentManager.findFragmentById(R.id.fragment_detail_container) == null) {
                loadLandscapeFragments();
            }
        } else {
            if (fragmentManager.findFragmentById(R.id.fragment_container) == null) {
                loadPortraitFragment();
            }
        }
    }

    // Loads the landscape fragments (list on the left, details on the right)
    private void loadLandscapeFragments() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        // Load the list fragment into the left container
        if (fragmentManager.findFragmentById(R.id.fragment_list_container) == null) {
            fragmentTransaction.replace(R.id.fragment_list_container, new ItemFragment());
        }

        // Load the detail fragment into the right container
        if (selectedNewsTitle != null) {
            // If a news item was selected, load the corresponding details
            NewsDetailFragment detailFragment = NewsDetailFragment.newInstance(selectedNewsTitle, selectedNewsDetails, selectedNewsImageResId);
            fragmentTransaction.replace(R.id.fragment_detail_container, detailFragment);
        } else {
            // Otherwise, load a blank detail fragment
            fragmentTransaction.replace(R.id.fragment_detail_container, new NewsDetailFragment());
        }

        fragmentTransaction.commitAllowingStateLoss();
    }

    // Loads the portrait fragment (list takes the full screen)
    private void loadPortraitFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        if (fragmentManager.findFragmentById(R.id.fragment_container) == null) {
            fragmentTransaction.replace(R.id.fragment_container, new ItemFragment());
            fragmentTransaction.commitAllowingStateLoss();
        }
    }

    // Refreshes the layout when the orientation changes
    private void refreshLayout() {
        View rootView = findViewById(android.R.id.content);
        if (rootView != null) {
            rootView.requestLayout();
            rootView.invalidate();
        }
    }

    // Handles news item clicks and displays the details in the appropriate fragment
    @Override
    public void onNewsItemClicked(String newsTitle, String newsDetails, int newsImageResId) {
        // Save the clicked item details for landscape mode restoration
        selectedNewsTitle = newsTitle;
        selectedNewsDetails = newsDetails;
        selectedNewsImageResId = newsImageResId;

        NewsDetailFragment detailFragment = NewsDetailFragment.newInstance(newsTitle, newsDetails, newsImageResId);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        if (isLandscape) {
            // Replace the right-side detail fragment in landscape mode
            fragmentTransaction.replace(R.id.fragment_detail_container, detailFragment);
        } else {
            // Replace the entire container in portrait mode and allow back navigation
            fragmentTransaction.replace(R.id.fragment_container, detailFragment)
                    .addToBackStack(null);
        }

        fragmentTransaction.commitAllowingStateLoss();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // Save the currently selected news item
        outState.putString("selectedNewsTitle", selectedNewsTitle);
        outState.putString("selectedNewsDetails", selectedNewsDetails);
        outState.putInt("selectedNewsImageResId", selectedNewsImageResId);
    }
}
