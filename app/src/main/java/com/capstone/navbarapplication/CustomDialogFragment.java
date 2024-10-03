package com.capstone.navbarapplication;

import android.app.Dialog;
import android.os.Bundle;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class CustomDialogFragment extends DialogFragment {

    private FragmentManager fragmentManager;

    // Default constructor
    public CustomDialogFragment() {}

    public static CustomDialogFragment newInstance(FragmentManager fragmentManager) {
        CustomDialogFragment fragment = new CustomDialogFragment();
        fragment.fragmentManager = fragmentManager;
        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Positive for Fragment & Negative for App Exit")
                .setPositiveButton("+", (dialog, id) -> {
                    if (fragmentManager != null) {
                        // Switch to MenuDisplayFragment when + is pressed
                        MenuDisplayFragment displayFragment = new MenuDisplayFragment();
                        FragmentTransaction transaction = fragmentManager.beginTransaction();
                        transaction.replace(R.id.fragment_container, displayFragment);
                        transaction.addToBackStack(null);
                        transaction.commit();
                    }
                })
                .setNegativeButton("−", (dialog, id) -> {
                    if (getActivity() != null) {
                        getActivity().finish();  // Close the app
                    }
                });
        return builder.create();
    }
}
